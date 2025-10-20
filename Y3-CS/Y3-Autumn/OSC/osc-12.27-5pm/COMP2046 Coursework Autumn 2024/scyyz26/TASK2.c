#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/types.h>
#include <time.h>
#include <unistd.h>
#include <wait.h>

// Shared memory structure
#define SHM_KEY 1234
#define MAX_ITERATIONS 10

struct SharedMemory {
    int rand_int;                      // Current random number
    int history[MAX_ITERATIONS * 2];  // Stores all intermediate results
    int index;                        // Current operation count
    int turn;                         // Controls the current operation (0 for ChildP1, 1 for ChildP2)
    int round;                        // Current round
};

int main()
{
    srand(time(NULL));                // Seed the random number generator

    // Create shared memory
    int shm_id = shmget(SHM_KEY, sizeof(struct SharedMemory), IPC_CREAT | 0666);
    if (shm_id < 0) {
        perror("shmget");
        exit(1);
    }

    // Attach to shared memory
    struct SharedMemory *shm = (struct SharedMemory *)shmat(shm_id, NULL, 0);
    if (shm == (void *)-1) {
        perror("shmat");
        exit(1);
    }

    // Initialize shared memory
    shm->rand_int = rand() % 20 + 1;  // Generate a random number between 1 and 20
    shm->index = 0;
    shm->turn = 0;  // Initial turn belongs to ChildP1
    shm->history[shm->index] = shm->rand_int;
    shm->round = 1;

    printf("The RandInt = %d, created by the the parent process\n", shm->rand_int);

    // Create child process ChildP1
    if (fork() == 0) {
        execl("./ChildP1", "./ChildP1", NULL);
        perror("execlp ChildP1");
        exit(1);
    }

    // Create child process ChildP2
    if (fork() == 0) {
        execl("./ChildP2", "./ChildP2", NULL);
        perror("execlp ChildP2");
        exit(1);
    }

    // Wait for child processes to complete
    wait(NULL);
    wait(NULL);

    // Parent process outputs final results
    printf("Final RandInt = %d\n", shm->history[MAX_ITERATIONS * 2 - 1]);
    for (int i = 0; i < MAX_ITERATIONS; i++) {
        printf("In round %d, RandInt = %d, parent process\n", i + 1, shm->history[i * 2 + 1]);
    }
    
    // Remove shared memory
    shmdt(shm);
    shmctl(shm_id, IPC_RMID, NULL);

    return 0;
}
