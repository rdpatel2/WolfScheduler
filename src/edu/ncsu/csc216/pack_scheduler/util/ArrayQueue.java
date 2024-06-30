/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Uses an ArrayList to queue elements
 * 
 * @param <E> Type of element in the ArrayQueue
 */
public class ArrayQueue<E> implements Queue<E> {

	/** the list used to store elements */
	private ArrayList<E> list;
	
	/** maximum number of elements that can be in a Queue */
	private int capacity;
	
	@Override
	public void enqueue(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
	}

	@Override
	public E dequeue() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
	/**
	 * Constructs the ArrayQueue with a new list and capacity
	 * 
	 * @param capacity to set the ArrayQueue to
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
}
