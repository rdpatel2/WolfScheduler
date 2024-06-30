/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests methods for ArrayStack
 * 
 * @author Parker Morrison
 */
class ArrayStackTest {

	/**
	 * tests push and pop for ArrayStack
	 */
	@Test
	void testPushPop() {
		ArrayStack<String> ls = new ArrayStack<String>(10);
		
		assertTrue(ls.isEmpty());
		
		assertEquals(0, ls.size());
		assertTrue(ls.isEmpty());
		ls.push("A");
		assertEquals(1, ls.size());
		ls.push("B");
		ls.push("C");
		ls.push("D");
		assertEquals(4, ls.size());
		
		assertEquals("D", ls.pop());
		assertEquals(3, ls.size());
		assertEquals("C", ls.pop());
		assertEquals("B", ls.pop());
		assertEquals("A", ls.pop());
		
		assertThrows(EmptyStackException.class, () -> ls.pop());
		
	}
	
	/**
	 * test setCapacity for ArrayStack
	 */
	@Test
	void testSetCapacity() {
		ArrayStack<String> ls = new ArrayStack<String>(0);
		
		assertThrows(IllegalArgumentException.class, () -> ls.setCapacity(-1));
		
		ls.setCapacity(2);
		ls.push("A");
		ls.push("B");
		assertThrows(IllegalArgumentException.class, () -> ls.push("C"));
		assertThrows(IllegalArgumentException.class, () -> ls.setCapacity(1));
		
		
	}

}
