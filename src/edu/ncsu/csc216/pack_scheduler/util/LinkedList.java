package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Creates a new LinkedList Object
 * @param <E> Type of element in the list
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	
	/** the number of elements in the LinkedList*/
	private int size;
	
	/** the ListNode at the front of the list*/
	private ListNode front;
	
	/** the ListNode at the back of the list*/
	private ListNode back;
	
	/**
	 * Creates a new LinkedList object
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		size = 0;
	}
	
	/**
	 * Creates a new ListIterator Object
	 * @param index where the list iterator is placed
	 * @return the created ListIterator
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return new LinkedListIterator(index);
	}
	
	/**
	 * Gets the size
	 * @return size of the LinkedList
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Adds a new element to the LinkedList
	 * @param index the index where the element is being added
	 * @param element the data inside the ListNode thats being added
	 */
	@Override
	public void add(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		
		super.add(index, element);
	}



	/**
	 * Sets the ListNodes data at the given index to the given element
	 * @param index the index being replaced
	 * @param element the data thats replacing the ListNode at index given
	 * @return the old data 
	 */
	@Override
	public E set(int index, E element) {
		if (this.contains(element)) {
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return super.set(index, element);
	}



	/**
	 * Creates a new ListNode object for the Linked List Class
	 */
	private class ListNode { 
		/** data inside the ListNode*/
		public E data;
		/** the next ListNode in the list*/
		public ListNode next;
		/** the previous ListNode in the list*/
		public ListNode prev;
		
		
		/**
		 * creates a listnode object
		 * @param data the data inside the ListNode
		 */
		public ListNode(E data){
			this.data = data;
			this.prev = null;
			this.next = null;
		}
		
		/**
		 * Creates a ListNode Object altering the previous and next links
		 * @param data Data inside the list node
		 * @param prev the listnode that should come before it
		 * @param next the listnode that should come after it
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
	}
	
	
	/**
	 * Creates a LinkedListIterator object to iterate through the linked list
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** The List Node before the LinkedListIterator*/
		public ListNode previous;
		
		/** The ListNode after the LinkedListIterator*/
		public ListNode next;
		
		/** The node that the LinkedListIterator last retrieved*/
		private ListNode lastRetrieved;
		
		/** holds the index for the previous node*/
		public int previousIndex;
		
		/** holds the index for the next node*/
		public int nextIndex;
		
		/**
		 * Creates a new LinkedListIterator in the index given in the parameter
		 * @param index the index where the LinkedListIterator should be placed
		 */
		public LinkedListIterator(int index) { 
			if(index < 0 || index > size()) {
				throw new IndexOutOfBoundsException("Iterator OOBs");
			}
			
			previous = front;
			next = previous.next;
			
			previousIndex = -1;
			nextIndex = 0;
					
			if (index != 0) {
		        for(int i = 0; i < index; i++) {
		            if (next == null) {
		                throw new NoSuchElementException("No next element");
		            }
		            previous = next;
		            next = next.next;
		            previousIndex++;
		            nextIndex++;
		        }
		    }
			
			lastRetrieved = null;
		}
		
		/**
		 * Checks if there is a ListNode after the LinkedListIterator
		 * @return true if there is, false if not
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		/**
		 * Gets the next ListNode
		 * @return the next ListNode
		 */
		@Override
		public E next() {
			if(next.data == null) {
				throw new NoSuchElementException("No next element");
			}
			
			E oldElement = next.data;
			lastRetrieved = next;
			
			previousIndex++;
			nextIndex++;
			
			next = next.next;
			
			
			return oldElement;
		}

		/**
		 * Checks if the LinkedListIterator has a ListNode before it
		 * @return true if there is, false if not
		 */
		@Override
		public boolean hasPrevious() {
			return previous.prev != null;
		}

		/**
		 * Return the previous ListNode
		 * @return the ListNode before the LinkedListIterator
		 */
		@Override
		public E previous() {
			if(previous.data == null) {
				throw new NoSuchElementException("No previous element");
			}
			
			E oldElement = previous.data;
			lastRetrieved = previous;
			
			previousIndex--;
			nextIndex--;
			
			previous = previous.prev;
		
			return oldElement;
		}

		/**
		 * Gets the nextIndex
		 * @return nextIndex
		 */
		@Override
		public int nextIndex() {
			
			if (next == null) {
				return size;
			}
			return nextIndex;
		}

		/**
		 * Gets the previous index
		 * @return the previous index
		 */
		@Override
		public int previousIndex() {
			if (previous == null) {
				return -1;
			}
			
			return previousIndex;
		}

		/**
		 * Removes the last retrieved ListNode
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			lastRetrieved = next;
			previous.next = previous.next.next;
			
			size--;
		}

		/**
		 * Sets the last retrieved ListNode to the data given
		 * @param e what the last retrieved ListNodes data is set to
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}

		/**
		 * Adds a new listNode
		 * @param e the data inside the ListNode being added
		 */
		@Override
		public void add(E e) {
			if (e == null) {
		        throw new NullPointerException("Null in add");
		    }

		    ListNode temp = new ListNode(e, previous, next);
		    
		    previous.next = temp;
		    next.prev = temp;
		  
		    size++;
		    lastRetrieved = null;
		    
//		    back.prev.next = temp;
//		    back.prev = temp;
//
//		    lastRetrieved = null;
//		    size++;
		}
		
	}



}
