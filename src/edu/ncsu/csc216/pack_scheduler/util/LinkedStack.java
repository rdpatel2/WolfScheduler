/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Creates a new Stack Object using the AbstractLinkedList class
 * @author Rohan Patel
 * @author Parker Morrison
 * 
 * @param <E> type of element the LinkedStack holds
 */
public class LinkedStack<E> implements Stack<E> {
	
	/**
	 * New LinkedAbstractList
	 */
	private LinkedAbstractList<E> list; 

	@Override
	public void push(E element) {
		if (size() == list.capacity) {
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
		list.setCapacity(capacity);
	}
	
	/**
	 * Creates a new Linked Stack object
	 * @param capacity capacity for the Linked Stack object
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

}
