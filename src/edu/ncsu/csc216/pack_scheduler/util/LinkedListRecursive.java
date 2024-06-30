package edu.ncsu.csc216.pack_scheduler.util;

/**
 * LinkedList that uses recursion to iterate through it's methods
 * @param <E> Type of element LinkedListRecursive uses
 */
public class LinkedListRecursive<E> {
	
	/** ListNode at the front of the LinkedList */
	private ListNode front;
	/** The size of the list */
	public int size;

	/**
	 * Constructor that sets the front to null and the size to 0
	 */
	public LinkedListRecursive(){
		front = null;
		size = 0;
	}
	
	/**
	 * Gets the size of the list
	 * 
	 * @return the size of the list as and integer
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Determines if the list is empty
	 * 
	 * @return returns true if the list is empty, otherwise, false
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Determines if the list contains the given element
	 * 
	 * @param element the element to check if the list contains
	 * @return true if the list contains the element, otherwise, false
	 */
	public boolean contains(E element) {
		if(isEmpty()) {
			return false;
		}
		
		return front.contains(element);
		
	} 	
	
	/**
	 * Base case for adding an element to the end of the list
	 * @param element the element to be added to the list
	 * @return true if the element is added, otherwise, false
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public boolean add(E element) {
		if(isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		}
		
		if(element == null) {
			throw new NullPointerException("Null element");
		}
		
		if(contains(element)) {
			throw new IllegalArgumentException("Element already exists");
		}
		
		return front.add(element);
	}
	
	/**
	 * Adds an element to the list at the given index
	 * @param element element to add to the list
	 * @param idx index to add the element at
	 * @return true if the element is added, otherwise, false
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than size
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if the element already exists in the list
	 */
	public boolean add(int idx, E element) {
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		if(element == null) {
			throw new NullPointerException("Null element");
		}
		
		if(contains(element)) {
			throw new IllegalArgumentException("Element already exists");
		}
		
		if(idx == 0) {
			ListNode newNode = new ListNode(element, front);
			front = newNode;
			size++;
			return true;
		}
		
		return front.add(idx, element);
	}
	
	/**
	 * Gets the element at the specified index
	 * @param idx the index to get the value at
	 * @return the value at the given index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than size
	 */
	public E get(int idx) {
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		if(idx == 0) {
			return front.data;
		}
		
		return front.next.get(idx - 1);
	}
	
	/**
	 * Removes the element at the specified index
	 * @param idx the index to remove the element at
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than size
	 */
	public E remove(int idx) {
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		if(idx == 0) {
			E rtn = front.data;
			front = front.next;
			size--;
			return rtn;
		}
		
		return front.remove(idx);
	}
	
	/**
	 * Removes the element in the list that matches the given element
	 * @param element the element to be removed
	 * @return true if the element is removed, otherwise, false
	 */
	public boolean remove(E element) {
		if(isEmpty()) {
			return false;
		}
		
//		if(element == null) {
//			throw new NullPointerException("Null element");
//		}
		
		
		if(front.data == element) {
			front = front.next;
			size--;
			return true;
		}
		
		return front.remove(element);
	}
	
	
	/**
	 * Sets the element at the given index to the new element
	 * @param idx the index to replace the element of
	 * @param element the element replacing the old element
	 * @return the value of the old element
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than size
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if the element already exists in the list
	 * 
	 */
	public E set(int idx, E element) {
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		
		if(element == null) {
			throw new NullPointerException("Null element");
		}
		
		if(contains(element)) {
			throw new IllegalArgumentException("Element already exists");
		}
		
		if(idx == 0) {
			E rtn = front.data;
			front.data = element;
			
			return rtn;
		}
		
		return front.set(idx, element);
	}
	
	
	
	/**
	 * A node that holds data and can be linearly connected to another node
	 */
	private class ListNode {
		/** The data the node holds */
		public E data;
		/** the next ListNode in the linked list */
		public ListNode next;
		
		
		/**
		 * Constructs a new ListNode with data and a next node
		 * @param data the data this node will hold
		 * @param next the reference to the next node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * Determines if the current node contains the given element
		 * @param element the element to check against this node
		 * @return the return statement of the next node
		 */
		public boolean contains(E element) {
			if(data == element) {
				return true;
			}
			
			if(next == null) {
				return false;
			}
						
			return next.contains(element);
		}
		
		/**
		 * Adds the element to this node if it is the last in the list
		 * @param element the element to be added to the back of the list
		 * @return true if the node is added, otherwise, false
		 */
		public boolean add(E element) {
			if(next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			}
			
			return next.add(element);
		}
		
		/**
		 * Checks if this node is the one to add the element to
		 * @param element the element to be added
		 * @param idx how far away the index is where the node is to be added
		 * @return true if this is the node to be added to, otherwise, add to the next node
		 */
		public boolean add(int idx, E element) {
			if(idx == 1) {
				ListNode newNode = new ListNode(element, next);
				next = newNode;
				size++;
				return true;
			}
			
			return next.add(idx - 1, element);
			
			
		}
		
		/**
		 * Determines if this is the node that the element gets
		 * @param idx how far away from the returned index is
		 * @return the data if index is 0, otherwise, check the next node
		 */
		public E get(int idx) {
			if(idx == 0) {
				return data;
			}
			
			return next.get(idx - 1);
		}
		
		/**
		 * Determines if this is the node that the element is removed at
		 * @param idx how far away the index is from the node it wants to remove
		 * @return the value to be removed if this is the node to be removed, otherwise, passes the
		 * statement on to the next node
		 */
		public E remove(int idx) {
			if(idx == 1) {
				E rtn = next.data;
				next = next.next;
				size--;
				return rtn;
			}
			
			return next.remove(idx - 1);
		}
		
		/**
		 * Removes the next element if it matches the data of the given element
		 * @param element the element to check against the data of this node
		 * @return true if the next element matches the data, false if at the end of the list,
		 * otherwise, checks the next node
		 */
		public boolean remove(E element) {
			if(next == null) {
				return false;
			}
			
			if(next.data == element) {
				next = next.next;
				size--;
				return true;
			}
			
			return next.remove(element);
		}
		
		/**
		 * Sets the data of this node to the given element if it is the desired index
		 * @param idx how far away this recursion is from the desired node
		 * @param element the element to replace the data of the node
		 * @return data if this is the desired node, otherwise check the next node
		 */
		public E set(int idx, E element) {
			if(idx == 0) {
				E rtn = data;
				data = element;
				
				return rtn;
			}
			
			return next.set(idx - 1, element);
		}
		
		
		
		
		
	}

}
