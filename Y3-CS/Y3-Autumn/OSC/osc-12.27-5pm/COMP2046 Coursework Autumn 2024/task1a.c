#include <stdio.h>
#include <stdlib.h>
#include <semaphore.h>
#include <pthread.h>
#include <unistd.h>
#include "linkedlist.h"


#define MAX_BUFFER_SIZE 50

#define MAX_NUMBER_OF_JOBS 100

// stating the variables

int count = 0; 
int produced = 0; 
int consumed = 0; 




// in order to print out the outputs 

void display(char * ProducerConsumer) 
{ 
  
    // printing out the output in the following format below 
    printf("%s, Produced = %d, Consumed = %d: ", ProducerConsumer, produced, consumed); 
    
    //write your code here
  
}

// for the consumer 

void * consumer(void * Number)
{
     	// output for the consumer is printed out via output function 
	//write your code here
        display("Consumer"); 
	//write your code here

        
}

void * producer(void * Number)
{ 
    	// write your code here
        // output for the producer is printed out via output function
        display("Producer"); 
       
	// write your code here

       
}

int main(int argc, char ** argv)
{ 
    //write your code here

    // initialising the semaphores
 
    // defining threads 


    return 0; 
}
