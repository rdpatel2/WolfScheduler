package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListRecursiveTest {

	/**
	 * Tests the constructor for LinkedList
	 */
	@Test
	public void testLinkedList() {
		LinkedListRecursive<String> a = new LinkedListRecursive<String>();
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add method of LinkedList
	 */
	@Test
	public void testAdd() {
		LinkedListRecursive<String> a = new LinkedListRecursive<String>();
		a.add("word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		Exception e1 = assertThrows(NullPointerException.class, () -> a.add(0, null));
		assertEquals("Null element", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> a.add(1, "word"));
		assertEquals("Element already exists", e2.getMessage());
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, "another"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(4, "second"));
		
		a.add("word2");
		a.add("word3");
		a.add("word5");
		a.add("word6");
		a.add(3, "word4");
		assertEquals("word4", a.get(3));
	}
	
	/**
	 * Tests the remove method of LinkedList
	 */
	@Test
	public void testRemove() {
		LinkedListRecursive<String> a = new LinkedListRecursive<String>();
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));	
		a.remove(0);
		assertEquals(0, a.size());
		a.add("word1");
		a.add("word2");
		a.add("word3");
		a.add("word4");
		
		a.remove(2);
		assertEquals(3, a.size());
		assertEquals("word1", a.get(0));
		assertEquals("word2", a.get(1));
		assertEquals("word4", a.get(2));
		
		
		a.add("word5");
		
		assertEquals("word1", a.get(0));
		assertEquals("word2", a.get(1));
		assertEquals("word4", a.get(2));
		assertEquals("word5", a.get(3));
		
		a.remove("word2");
		
		assertEquals("word1", a.get(0));
		assertEquals("word4", a.get(1));
		assertEquals("word5", a.get(2));
	}

	
	/**
	 * Tests the set method of LinkedList
	 */
	@Test
	public void testSet() {
		LinkedListRecursive<String> a = new LinkedListRecursive<String>();
		
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
		LinkedListRecursive<String> a = new LinkedListRecursive<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
		
		a.add("Orange");
		a.add("Banana");
		a.add("Apple");
		a.add("Kiwi");
		
//		System.out.println(a.toString());
//		System.out.println(a.lastIndexOf("Orange"));
//		System.out.println(a.get(1));
	}
	
	

}
