package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests ArrayQueue for it's methods
 */
class ArrayQueueTest {

	/**
	 * Tests ArrayQueue for enqueueing and dequeueing
	 */
	@Test
	void testEnqueueDequeue() {
		ArrayQueue<String> ls = new ArrayQueue<String>(10);
		
		assertTrue(ls.isEmpty());
		
		assertEquals(0, ls.size());
		assertTrue(ls.isEmpty());
		ls.enqueue("A");
		assertEquals(1, ls.size());
		ls.enqueue("B");
		ls.enqueue("C");
		ls.enqueue("D");
		assertEquals(4, ls.size());
		
		assertEquals("A", ls.dequeue());
		assertEquals(3, ls.size());
		assertEquals("B", ls.dequeue());
		assertEquals("C", ls.dequeue());
		assertEquals("D", ls.dequeue());
		
		assertThrows(NoSuchElementException.class, () -> ls.dequeue());
		
	}
	
	/**
	 * Tests ArrayQueue for setCapacity
	 */
	@Test
	void testSetCapacity() {
		ArrayQueue<String> ls = new ArrayQueue<String>(10);
		
		assertThrows(IllegalArgumentException.class, () -> ls.setCapacity(-1));
		
		ls.setCapacity(2);
		ls.enqueue("A");
		ls.enqueue("B");
		assertThrows(IllegalArgumentException.class, () -> ls.enqueue("C"));
		assertThrows(IllegalArgumentException.class, () -> ls.setCapacity(1));
		
		
	}

}
