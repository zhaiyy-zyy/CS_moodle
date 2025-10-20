#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "linkedlist.h"

#define MAX_BUFFER_SIZE 10
#define MAX_NUMBER_OF_JOBS 100

// Declaring global variables
int count = 0;
int produced = 0;
int consumed = 0;

char data = '*';
struct element *head = NULL;  // Linked list head pointer
struct element *tail = NULL;  // Linked list tail pointer

// Semaphores for synchronization
sem_t empty;            // Semaphore to track empty buffer slots
sem_t full;             // Semaphore to track full buffer slots
pthread_mutex_t mutex;  // Mutex to protect access to the buffer

// Function to display the current state
void display(char *ProducerConsumer)
{
    // Printing the output in the specified format
    printf("%s, Produced = %d, Consumed = %d", ProducerConsumer, produced, consumed);
    struct element *current = head;
    while (current != NULL) {
        printf("%c", *((char *)current->pData));
        current = current->pNext;
    }
    printf("\n");
}

// Consumer thread function
void *consumer(void *Number)
{
    // Consumer processes jobs and prints output via the display function
    for (int i = 0; i < MAX_NUMBER_OF_JOBS; i++) {
        sem_wait(&full);             // Wait until there is an item in the buffer
        pthread_mutex_lock(&mutex);  // Lock the mutex to enter the critical section
        removeFirst(&head, &tail);   // Remove an item from the buffer
        consumed++;                  // Increment the consumed count
        display("Consumer");
        pthread_mutex_unlock(&mutex);  // Unlock the mutex to leave the critical section
        sem_post(&empty);              // Signal that a buffer slot is now empty
    }
    return NULL;
}

// Producer thread function
void *producer(void *Number)
{
    // Producer adds items to the buffer and prints output
    for (int i = 0; i < MAX_NUMBER_OF_JOBS; i++) {
        sem_wait(&empty);                      // Wait until there is space in the buffer
        pthread_mutex_lock(&mutex);            // Lock the mutex to enter the critical section
        addLast((void *)&data, &head, &tail);  // Add an item to the buffer
        produced++;                            // Increment the produced count
        display("Producer");
        pthread_mutex_unlock(&mutex);  // Unlock the mutex to leave the critical section
        sem_post(&full);               // Signal that a buffer slot is now empty
    }
    return NULL;
}

int main(int argc, char **argv)
{
    // Open the file for writing output
    FILE *outFile = freopen("task1a.txt", "w", stdout);
    if (outFile == NULL) {
        perror("Failed to open file for writing");
        exit(1);
    }

    // Define producer and consumer threads
    pthread_t producerThread, consumerThread;

    // Initialize semaphores
    sem_init(&empty, 0, MAX_BUFFER_SIZE);
    sem_init(&full, 0, 0);
    // Initialize mutex
    pthread_mutex_init(&mutex, NULL);

    // Create producer and consumer threads
    pthread_create(&producerThread, NULL, producer, NULL);
    pthread_create(&consumerThread, NULL, consumer, NULL);

    // Wait for producer and consumer threads to complete
    pthread_join(producerThread, NULL);
    pthread_join(consumerThread, NULL);

    // Destroy semaphores and mutex
    sem_destroy(&empty);
    sem_destroy(&full);
    pthread_mutex_destroy(&mutex);

    // Close the file
    fclose(outFile);
    
    return 0;
}
