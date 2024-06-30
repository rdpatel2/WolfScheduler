package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A linked list implementation.
 * @author Brendon Hablutzel
 * @param <E> the type that the list contains
 */
public class LinkedAbstractList<E> extends AbstractList<E>  {
	/**
	 * The first node in the linked list
	 */
	ListNode front;
	/**
	 * The last node in the linked list
	 */
	ListNode back;
	/**
	 * The size of the linked list
	 */
	int size;
	/**
	 * The capacity of the linked list
	 */
	int capacity;
	
	/**
	 * Creates a new LinkedAbstractList with the given capacity
	 * @param capacity the initial capacity to set
	 * @throws IllegalArgumentException if the capacity is less than 0 or less than the size of the list
	 */
	public LinkedAbstractList(int capacity) {
		this.front = null;
		this.back = null;
		this.size = 0;
		setCapacity(capacity);
	}
	
	/**
	 * Gets the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Sets the capacity for this linked list
	 * @param capacity the new capacity for the list
	 * @throws IllegalArgumentException if the capacity is less than 0 or less than the size of the list
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < this.size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
	/**
	 * Adds an element at the given index
	 * @param idx the index to add the element at
	 * @param data the data to add at the given index
	 * @throws NullPointerException if the data to add is null
	 * @throws IllegalArgumentException if the size is equal to the capacity
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size of the list
	 */
	@Override
	public void add(int idx, E data) {
		ListNode current;
		if (data == null) {
			throw new NullPointerException();
		}
		
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < size(); i++) {
			if (get(i) == data) {
				throw new IllegalArgumentException();
			}
		}
		
		if (idx == 0) {
			if (front == null) {
				front = new ListNode(data);
			} else {
				front = new ListNode(data, front);
			}
		} else {
			current = front;
			for (int i = 0; i < idx; i++) {
				if (i == idx - 1) {
					ListNode oldNext = current.next;
					current.next = new ListNode(data, oldNext);
				}
				current = current.next;
			}
		}
		current = front;
		for (int i = 0; i < size - 1; i++) {
			current = current.next;
		}
		back = current;
		size++;
	}
	
	/**
	 * Removes the element at the given index
	 * @param idx the index of the element to remove
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to size
	 */
	@Override
	public E remove(int idx) {
        // Bounds check
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException();
        }

        // Special case: removing from the front
        if (idx == 0) {
            E data = front.data;
            front = front.next;
            size--;
            // Update back if removing the last element
            if (size == 0) {
                back = null;
            }
            return data;
        }

        // General case
        ListNode current = front;
        for (int i = 0; i < idx - 1; i++) {
            current = current.next;
        }
        E data = current.next.data;
        current.next = current.next.next;
        size--;
        // Update back if removing the last element
        if (idx == size()) {
            back = current;
        }
        return data;
    }	
	
	/**
	 * Gets the data in the list at the given index
	 * @param idx the index of the data to get
	 * @return the data at the given index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to the size of the list
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode current = front;
		if (current == null) {
			return null;
		}
		
		for (int i = 0; i <= idx; i++) {
			if (i == idx) {
				return current.data;
			}
			current = current.next;
		}
		
		return null;
	}
	
	@Override
	public E set(int idx, E data) {
		if (data == null) {
			throw new NullPointerException();
		}
		
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < size(); i++) {
			if (get(i) == data) {
				throw new IllegalArgumentException();
			}
		}
		
		if (idx == 0) {
			if (front == null) {
				front = new ListNode(data);
			} else {
				E oldData = front.data;
				front = new ListNode(data, front.next);
				return oldData;
			}
		} else {
			ListNode current = front;
			for (int i = 0; i < idx; i++) {
				if (i == idx - 1) {
					ListNode oldNext = current.next;
					current.next = new ListNode(data, oldNext.next);
					return oldNext.data;
				}
				current = current.next;
			}
		}
		
		return null;
	}
	
	/**
	 * A node in the linked list
	 * @author Brendon Hablutzel
	 */
	private class ListNode {
		/**
		 * The data contained by the node
		 */
		E data;
		/**
		 * The next node in the list, or null if there is no such node
		 */
		ListNode next;
		
		public ListNode(E data) {
			this.data = data;
		}
		
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
	
}
