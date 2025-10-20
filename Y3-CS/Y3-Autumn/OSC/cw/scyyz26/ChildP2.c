#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>

#define SHM_KEY 1234
#define MAX_ITERATIONS 10

// Shared memory structure
struct SharedMemory {
    int rand_int;                      // Current random number
    int history[MAX_ITERATIONS * 2];  // Stores all intermediate results
    int index;                        // Current operation count
    int turn;                         // Controls the current operation (0 for ChildP1, 1 for ChildP2)
    int round;                        // Current round
};

int main()
{
    // Attach to the shared memory
    int shm_id = shmget(SHM_KEY, sizeof(struct SharedMemory), 0666);
    if (shm_id < 0) {
        perror("shmget");
        exit(1);
    }

    struct SharedMemory *shm = (struct SharedMemory *)shmat(shm_id, NULL, 0);
    if (shm == (void *)-1) {
        perror("shmat");
        exit(1);
    } 

    for (int i = 0; i < MAX_ITERATIONS; i++) {
        // Wait for the turn
        while (shm->turn != 1);

        // Subtract 10 from the random number
        shm->rand_int -= 10;
        shm->history[shm->index++] = shm->rand_int;
        printf("In round %d, RandInt = %d, child process 2\n", shm->round++, shm->rand_int);
        // Pass the turn back to ChildP1
        shm->turn = 0;
    }

    shmdt(shm);
    return 0;
}
