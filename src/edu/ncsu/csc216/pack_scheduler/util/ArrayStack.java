/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates a new Stack using the ArrayList class
 * @author Rohan Patel
 * @author Parker Morrison
 * 
 * @param <E> the type of element that the ArrayStack holds
 */
public class ArrayStack<E> implements Stack<E> {
	
	/**
	 * New array list
	 */
	private ArrayList<E> list;
	
	/**
	 * Capacity of the list
	 */
	private int capacity;
	
	
	@Override
	public void push(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(element);
	}

	@Override
	public E pop() {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return list.remove(list.size() - 1);
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
	 * Creates a new ArrayStack object
	 * @param capacity capacity of the ArrayStack object
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}
}
