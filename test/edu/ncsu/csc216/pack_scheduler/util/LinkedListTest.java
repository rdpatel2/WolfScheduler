package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;
import org.junit.jupiter.api.Test;

class LinkedListTest {

	/**
	 * Tests the constructor for LinkedList
	 */
	@Test
	public void testLinkedList() {
		LinkedList<String> a = new LinkedList<String>();
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add method of LinkedList
	 */
	@Test
	public void testAdd() {
		LinkedList<String> a = new LinkedList<String>();
		a.add("word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		assertThrows(NullPointerException.class, () -> a.add(0, null));
		
		assertThrows(IllegalArgumentException.class, () -> a.add(1, "word"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, "another"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(4, "second"));
	}
	
	/**
	 * Tests the remove method of LinkedList
	 */
	@Test
	public void testRemove() {
		LinkedList<String> a = new LinkedList<String>();
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));	
		a.remove(0);
		assertEquals(0, a.size());
		
		
		LinkedList<String> ts = new LinkedList<String>();
		
		ts.add("orange");
		ts.add("banana");
		ts.add("apple");
		ts.add("kiwi");
		
		ts.remove(1);
		assertEquals("apple", ts.get(1));
		
	}

	
	/**
	 * Tests the set method of LinkedList
	 */
	@Test
	public void testSet() {
		LinkedList<String> a = new LinkedList<String>();
		
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		a.set(0, "another word");
		assertEquals(1, a.size());
		assertEquals("another word", a.get(0));
		
		assertThrows(NullPointerException.class, () -> a.set(0, null));
		
		assertThrows(IllegalArgumentException.class, () -> a.set(0, "another word"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.set(1, "invalid index"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.set(-1, "invalid index"));
		
	}
	
	/**
	 * Tests the get method of the LinkedList
	 */
	@Test
	public void testGet() {
		LinkedList<String> a = new LinkedList<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
		
		a.add("Orange");
		a.add("Banana");
		a.add("Apple");
		a.add("Kiwi");
		
//		System.out.println(a.toString());
//		System.out.println(a.lastIndexOf("Orange"));
//		System.out.println(a.get(1));
	}
	
	/**
	 * Tests LinkedListIterator methods
	 */
	@Test
	void testListTerator() {
		
		LinkedList<String> a = new LinkedList<String>();
		a.add("Apple");
		a.add("Banana");
		a.add("Cherry");
	
		ListIterator<String> iterator = a.listIterator(1);
		
		assertEquals(1, iterator.nextIndex());
		assertTrue(iterator.hasNext());
		assertTrue(iterator.hasPrevious());
		assertEquals("Apple", iterator.previous());
		assertEquals("Banana", iterator.next());
	}
}
