/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;
import java.util.NoSuchElementException;

/**
 * Creates a new Queue with elements of type E
 * @author Rohan Patel
 * @author Parker Morrison
 * 
 * @param <E> type of element held in the Queue
 */
public interface Queue<E> {

	/**
	 * Adds the element to the back of the Queue
	 * @param element element being added to the Queue
	 * @throws IllegalArgumentException if capacity is equal to size
	 */
	void enqueue(E element);
	
	/**
	 * Removes and returns the element at the front of the Queue
	 * @throws NoSuchElementException if queue is empty
	 * @return element at the front of the queue
	 */
	E dequeue();
	
	/**
	 * Checks if the queue is empty
	 * @return true if empty, false if else
	 */
	boolean isEmpty();
	
	/**
	 * Checks how many elements are in the queue
	 * @return the number of elements in the queue
	 */
	int size();
	
	/**
	 * Sets the capacity of the queue
	 * @param capacity capacity being set
	 */
	void setCapacity(int capacity);
	
	
}
