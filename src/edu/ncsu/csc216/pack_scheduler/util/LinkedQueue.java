/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue functionality using a LinkedList
 * 
 * @param <E> the type of element the LinkedQueue holds
 */
public class LinkedQueue<E> implements Queue<E> {

	/**
	 * New LinkedAbstractList
	 */
	private LinkedAbstractList<E> list; 

	@Override
	public void enqueue(E element) {
		if (size() == list.capacity) {
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
		list.setCapacity(capacity);
	}
	
	/**
	 * Creates a new Linked Stack object
	 * @param capacity capacity for the Linked Stack object
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * Checks the given element against the rest of the elements in the list to see if it already contains that element
	 * 
	 * @param element Element to check against the Queue
	 * @return true if the queue contains the element, otherwise, false
	 */
	public boolean contains(E element) {
		for(int i = 0; i < list.size(); i++) {
			if(element.equals(list.get(i))) {
				return true;
			}
		}
		
		return false;
	}

}
