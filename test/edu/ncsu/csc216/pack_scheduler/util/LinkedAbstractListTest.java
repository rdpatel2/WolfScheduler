package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

/**
 * Test class for the LinkedAbstractList class.
 * @author Brendon Hablutzel
 */
public class LinkedAbstractListTest {
	
	/**
	 * Tests the constructor for LinkedAbstractList
	 */
	@Test
	public void testLinkedAbstractList() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(0);
		assertEquals(0, a.size());
	}
	
	/**
	 * Tests the add method of LinkedAbstractList
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(2);
		assertThrows(NullPointerException.class, () -> a.add(0, null));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(1, "word"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, "another"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.add(4, "second"));

		
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		a.add(1, "another");
		assertEquals(2, a.size());
		assertEquals("another", a.get(1));
		
		assertThrows(IllegalArgumentException.class, () -> a.add(3, "greater than capacity"));
		
}
	
	/**
	 * Tests the remove method of LinkedAbstractList
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(5);
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));	
		
		a.remove(0);
		assertEquals(0, a.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(0));
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
	}

	
	/**
	 * Tests the set method of LinkedAbstractList
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(5);
		
		a.add(0, "word");
		assertEquals(1, a.size());
		assertEquals("word", a.get(0));
		
		a.add(1, "second word");
		assertEquals(2, a.size());
		assertEquals("second word", a.get(1));
		
		a.set(0, "another word");
		assertEquals(2, a.size());
		assertEquals("another word", a.get(0));
		
		a.set(1, "different word");
		assertEquals(2, a.size());
		assertEquals("different word", a.get(1));
		
		assertThrows(NullPointerException.class, () -> a.set(0, null));
		
		assertThrows(IllegalArgumentException.class, () -> a.set(0, "another word"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.set(2, "invalid index"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.set(-1, "invalid index"));
		
	}
	
	/**
	 * Tests the get method of the LinkedAbstractList
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(5);
		
		assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
	}
}
