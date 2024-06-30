/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;
import java.util.EmptyStackException;

/**
 * Creates a new Stack Interface
 * @author Rohan Patel
 * @author Parker Morrison
 * 
 * @param <E> The type of element the stack will contain
 */
public interface Stack<E> {
	
	/**
	 * Adds element to the top of the stack
	 * @throws IllegalArgumentException if there is no room in the stack
	 * @param element the element to be pushed to the stack
	 */
	void push(E element);
	
	/**
	 * Removes and returns the element at the top of the stack
	 * @throws EmptyStackException if stack is empty
	 * @return element at the top of the stack
	 */
	E pop();
	
	/**
	 * Checks if the stack is empty
	 * @return true if the stack is empty, false if not
	 */
	boolean isEmpty();
	
	/**
	 * Checks the size of the stack
	 * @return the number of elements in the stack
	 */
	int size();
	
	/**
	 * Sets the stacks capacity
	 * @throws IllegalArgumentException if capacity is negative or less than size
	 * @param capacity the capacity being set
	 */
	void setCapacity(int capacity);
}
