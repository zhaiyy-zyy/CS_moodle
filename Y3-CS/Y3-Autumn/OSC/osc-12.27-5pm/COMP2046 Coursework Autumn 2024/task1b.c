#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>
#include "linkedlist.h"


#define MAX_BUFFER_SIZE 50
#define MAX_NUMBER_OF_JOBS 100
#define NUMBER_OF_PRODUCERS 1
#define NUMBER_OF_CONSUMERS 4



// declaring the variables

int Consumed = 0; 
int Produced = 0; 


// declaring semaphores and initiliasing pointers


// function to print output

void display(char *ProducerConsumer, int Id) 
{
    printf("%s Id = %d, Produced = %d, Consumed = %d: ", ProducerConsumer, Id, Produced, Consumed);

    //write your code here

  
}

void * consumer(void *con)
{ 
    int ConsumerId = *(int*)con; // called in display function
    //write your code here
    display("Consumer", ConsumerId);
   //write you code here

            
}

void * producer(void *pro){
    
    int ProducerId = *(int*)pro;
    
    
    //write your code here   
    display("Producer", ProducerId);
   //write your code here

       
}
 
int main(int argc, char **argv)
{
    
    /*write your code here */

        //intialise the semaphores.


        //declare more variables


        //create consumer threads

  
        //create producer threads
    
   
    return 0;
}