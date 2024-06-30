package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Test class for the ArrayList class.
 */
public class ArrayListTest {
	
	/**
	 * Tests the constructor for ArrayList
	 */
	@Test
	public void testArrayList() {
		ArrayList<String> a = new ArrayList<String>();
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add method of ArrayList
	 */
	@Test
	public void testAdd() {
		ArrayList<String> a = new ArrayList<String>();
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		assertThrows(NullPointerException.class, () -> a.add(0, null));
		
		assertThrows(IllegalArgumentException.class, () -> a.add(1, "word"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, "another"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(4, "second"));
	}
	
	/**
	 * Tests the remove method of ArrayList
	 */
	@Test
	public void testRemove() {
		ArrayList<String> a = new ArrayList<String>();
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));	
		a.remove(0);
		assertEquals(0, a.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(0));
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
	}

	
	/**
	 * Tests the set method of ArrayList
	 */
	@Test
	public void testSet() {
		ArrayList<String> a = new ArrayList<String>();
		
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
	 * Tests the get method of the ArrayList
	 */
	@Test
	public void testGet() {
		ArrayList<String> a = new ArrayList<String>();
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
	}
}
