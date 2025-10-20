package main.utils;

public interface Queue<E> {
	/*Returns the number of elements in the queue*/
	int size();
	
	/*Indicates whether no elements are stored*/
	boolean isEmpty();
	
	/*inserts an element at the end of the queue*/
	void enqueue(E e);
	
	/*returns the element at the front without removing it*/
	E first();
	
	
	/*removes and returns the element at the front of the  queue*/
	E dequeue();
	
	
}
