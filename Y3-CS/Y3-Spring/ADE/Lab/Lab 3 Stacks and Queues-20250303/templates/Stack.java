package main.utils;

public interface Stack<E> {
	
	/*Adds element e to the top of the stack*/
	void push(E e);
	
	/*Removes and returns the top element from the stack; return null if the stack is empty. */
	E pop();
	
	/*Returns the top element of the stack, without removing it; return null if the stack is empty.*/
	E top();
	
	/*Returns the number of elements in the stack. */
	int size();
	
	/*Returns a boolean indicating whether the stack is empty. */
	boolean isEmpty();
}
