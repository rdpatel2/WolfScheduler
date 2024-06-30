package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;
/**
 * The ArrayList class creates an object array, and holds methods for adding, removing and getting functionalities.
 * @param <E> The object parameter for the ArrayList.
 * @author Evan Gregorius
 * @author Brendon Hablutzel
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/**
	 * The capacity of the array list.
	 */
	private static final int INIT_SIZE = 10;
	
	/**
	 * The list itself.
	 */
	private E[] list;
	
	/**
	 * The size of the array list.
	 */
	private int size;
	
	/**
	 * Constructor for the ArrayList class
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
//		E item = (E) new Object();
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Getter method to return the size of the array list.
	 * @return The size of the array.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Adds an element to the array at index E.
	 * @param index Index of the element to get.
	 * @param item Item to add. 
	 */
	@Override
	public void add(int index, E item) {
		if (list.length - size == 0) {
			growArray();
		}
		else if (item == null) {
			throw new NullPointerException();
		}
		
		for (E object : list) {
			if (item.equals(object)) {
				throw new IllegalArgumentException();
			}
		}
		
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = size - 1; i >= index; i--) {
			list[i + 1] = list[i];
		}
		
		list[index] = item;
		size++;
	}
	
	/**
	 * Private helper method to double to list capacity.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] newList = (E[]) new Object[list.length * 2];
		
		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		
		list = newList;
		
	}
	
	/**
	 * Removes the element at the index.
	 * @param index The index of the element to remove.
	 * @return The element that was removed.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E item = list[index];
		
		for (int i = index; i < size; i++) {
			list[i] = list[i + 1];
		}
		
		list[size - 1] = null;
		
		size--;
		
		return item;
	}
	
	/**
	 * Sets the value at the given index to the given value, replacing the value that was at that index
	 * @param index the index to set the data at
	 * @param data the data to set at the given index
	 * @return the value that was at the index previously
	 * @throws NullPointerException if the data to set is null
	 * @throws IllegalArgumentException if the data to set is already in the list
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to the size of the list
	 */
	public E set(int index, E data) {
		if (data == null) {
			throw new NullPointerException();
		}
		
		for (E item : list) {
			if (data.equals(item)) {
				throw new IllegalArgumentException();
			}
		}
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E oldItem = list[index];
		
		list[index] = data;
		
		return oldItem;
	}
	
	/**
	 * Gets the item at the specified index
	 * @param index the index of the item to get
	 * @return the value at the given index
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than the size the of the array
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return list[index];
	}
	
}
