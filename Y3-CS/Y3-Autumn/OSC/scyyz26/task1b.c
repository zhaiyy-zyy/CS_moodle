#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "linkedlist.h"

#define MAX_BUFFER_SIZE 10
#define MAX_NUMBER_OF_JOBS 100
#define NUMBER_OF_PRODUCERS 1
#define NUMBER_OF_CONSUMERS 4

// Declaring global variables
int Consumed = 0;
int Produced = 0;

int is_producer_done = 0;
char data = '*';
struct element *head = NULL;  // Linked list head pointer
struct element *tail = NULL;  // Linked list tail pointer

// Declaring semaphores and initializing pointers
sem_t empty;            // Semaphore to track empty buffer slots
sem_t full;             // Semaphore to track full buffer slots
pthread_mutex_t mutex;  // Mutex to protect access to the buffer


// Function to display the current state
void display(char *ProducerConsumer, int Id)
{
    printf("%s Id = %d, Produced = %d, Consumed = %d: ", ProducerConsumer, Id, Produced, Consumed);
    struct element *current = head;
    while (current != NULL) {
        printf("%c", *((char *)current->pData));
        current = current->pNext;
    }
    printf("\n");
}

// Consumer thread function
void *consumer(void *con)
{
    int ConsumerId = *(int *)con;
    while (1) {
        sem_wait(&full);             // Wait until the buffer has data
        pthread_mutex_lock(&mutex);  // Lock the mutex to enter critical section
        if (Consumed >= MAX_NUMBER_OF_JOBS && is_producer_done) {
            pthread_mutex_unlock(&mutex);
            sem_post(&full);  // Notify that a slot in the buffer is empty
            break;
        }
        if (Consumed < MAX_NUMBER_OF_JOBS) {
            removeFirst(&head, &tail);  // Remove data from the buffer
            Consumed++;                 // Update consumed count

            display("Consumer", ConsumerId);  // Display the current state
        }
        pthread_mutex_unlock(&mutex);  // Unlock the mutex to leave critical section
        sem_post(&empty);              // Notify that a slot in the buffer is empty
    }
    return NULL;
}

// Producer thread function
void *producer(void *pro)
{
    int ProducerId = *(int *)pro;
    while (1) {
        sem_wait(&empty);            // Wait until there is space in the buffer
        pthread_mutex_lock(&mutex);  // Lock the mutex to enter critical section
        if (Produced >= MAX_NUMBER_OF_JOBS) {
            is_producer_done = 1;  // Mark production as complete
            pthread_mutex_unlock(&mutex);
            sem_post(&full);  // Wake up any remaining consumers
            break;
        }
        addLast((void *)&data, &head, &tail);  // Add an element to the buffer
        Produced++;                            // Increment the produced count

        // output for the producer is printed out via output function
        display("Producer", ProducerId);
        pthread_mutex_unlock(&mutex);  // Unlock the mutex to leave critical section
        sem_post(&full);               // Notify that a buffer slot is full
    }
    return NULL;
}

int main(int argc, char **argv)
{
    // Open the file for writing output
    FILE *outFile = freopen("task1b.txt", "w", stdout);
    if (outFile == NULL) {
        perror("Failed to open file for writing");
        exit(1);
    }

    // Declare threads for producers and consumers
    pthread_t producerThread[NUMBER_OF_PRODUCERS];
    pthread_t consumerThread[NUMBER_OF_CONSUMERS];

    // Intialise the semaphores
    sem_init(&empty, 0, MAX_BUFFER_SIZE);
    sem_init(&full, 0, 0);

    // Initialise the mutex
    pthread_mutex_init(&mutex, NULL);

    // Declare the other variables: Consumer IDs
    int id[] = {0, 1, 2, 3};

    // Create producer threads
    for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
        pthread_create(&producerThread[i], NULL, producer, (void *)&id[i]);
    }
    // Create consumer threads
    for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
        pthread_create(&consumerThread[i], NULL, consumer, (void *)&id[i]);
    }
    //Wait for producer threads to finish
    for (int i = 0; i < NUMBER_OF_PRODUCERS; i++) {
        pthread_join(producerThread[i], NULL);
    }
    // Wait for consumer threads to finish
    for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
        pthread_join(consumerThread[i], NULL);
    }

    // Destroy semaphores and mutex
    sem_destroy(&empty);
    sem_destroy(&full);
    pthread_mutex_destroy(&mutex);

    // Close the file
    fclose(outFile);

    return 0;
}
