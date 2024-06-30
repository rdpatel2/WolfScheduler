package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import java.io.IOException;

import org.junit.Test;

//import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;

/**
 * A suite of methods for testing the SortedList class.
 * 
 * @author Brendon Hablutzel
 * @author Parker Morrison
 */
public class SortedListTest {

	/**
	 * Test that the SortedList can be correctly constructed and grows beyond 10
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		
		//Test that the list is constructed empty
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		assertTrue(list.isEmpty());
		
		
		//Test that the list grows by adding at least 11 elements
		//Remember the list's initial capacity is 10
		assertTrue(list.add("element1"));
		assertTrue(list.add("element2"));
		assertTrue(list.add("element3"));
		assertTrue(list.add("element4"));
		assertTrue(list.add("element5"));
		assertTrue(list.add("element6"));
		assertTrue(list.add("element7"));
		assertTrue(list.add("element8"));
		assertTrue(list.add("element9"));
		assertTrue(list.add("element10"));
		assertTrue(list.add("element11"));
	}

	/**
	 * Tests for adding an element to a SortedList. Tests invalid (null and duplicate) and valid cases.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		// Test adding to the front, middle and back of the list
		//Test adding an element to the front
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		
		//Test adding an element to the middle
		list.add("arm"); // in between apple and banana
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("arm", list.get(1));
		assertEquals("banana", list.get(2));
		
		//Test adding an element to the back
		list.add("pineapple"); // at the end of the list
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("arm", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("pineapple", list.get(3));
		
		// Test adding a null element
		assertThrows(NullPointerException.class, 
				() -> list.add(null)); // adds null to the list
		
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("arm", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("pineapple", list.get(3));
		
		// Test adding a duplicate element
		assertThrows(IllegalArgumentException.class, 
				() -> list.add("pineapple")); // duplicate element in the list
		
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("arm", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("pineapple", list.get(3));
	}
	
	/**
	 * Tests invalid cases for getting an element from a SortedList. This includes trying to get
	 * an element from an empty list, getting an element at a negative index, and getting an element
	 * beyond the size of the SortedList.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		
		// Test getting an element from an empty list
		assertEquals(0, list.size());
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(0)); // get an element from an empty array
		
		assertEquals(0, list.size());
		
		// Add some elements to the list
		list.add("apple");
		list.add("arm");
		list.add("banana");
		list.add("pineapple");
		
		assertEquals("apple", list.get(0));
		assertEquals("arm", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("pineapple", list.get(3));
		
		assertEquals(4, list.size());
		
		// Test getting an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(-1)); // get an element from out of bounds
		
		// Test getting an element at size
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(list.size())); // get an element from out of bounds
		
	}
	
	/**
	 * Test removing an element from a SortedList. Tests invalid cases such as removing from an empty list,
	 * removing at invalid indexes, and valid cases including adding at beginning, middle and end of the list.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		//Test removing from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		assertEquals(0, list.size());
		
		//Add some elements to the list - at least 4
		list.add("apple");
		list.add("arm");
		list.add("banana");
		list.add("pineapple");
		
		//Test removing an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertEquals(4, list.size());
		
		//Test removing an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		assertEquals(4, list.size());
		
		//Test removing a middle element
		list.remove(1);
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("pineapple", list.get(2));
		
		//Test removing the last element
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		
		//Test removing the first element
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//Test removing the last element in the SortedList
		list.remove(0);
		assertEquals(0, list.size());
	}
	
	/**
	 * Test getting the index of an element in a SortedList. Checks for where
	 * the element exists and does not exist, as well as when the desired element
	 * is null.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		//Test indexOf on an empty list
		assertEquals(0, list.size());
		assertEquals(-1, list.indexOf("apple"));
		
		//Add some elements
		list.add("apple");
		list.add("arm");
		list.add("banana");
		list.add("pineapple");
		
		//Test various calls to indexOf for elements in the list
		//and not in the list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("arm"));
		assertEquals(2, list.indexOf("banana"));
		assertEquals(3, list.indexOf("pineapple"));
		
		assertEquals(-1, list.indexOf("nope"));
		assertEquals(-1, list.indexOf("not"));
		
		//Test checking the index of null
		assertThrows(NullPointerException.class, () -> list.indexOf(null));
	}
	
	/**
	 * Tests that clear removes all elements from the SortedList.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("arm");
		list.add("banana");
		list.add("pineapple");
		
		// Clear the list
		list.clear();
		
		// Test that the list is empty
		assertEquals(0, list.size());
	}

	/**
	 * Tests that isEmpty only returns true for empty lists
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		// Test that the list starts empty
		assertTrue(list.isEmpty());
		
		// Add at least one element
		list.add("apple");
		
		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * Test that the contains method successfully checks whether an element is
	 * contained in the SortedList.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		// Test the empty list case
		assertFalse(list.contains("apple"));
		
		// Add some elements
		list.add("apple");
		list.add("arm");
		list.add("banana");
		list.add("pineapple");
		
		// Test some true and false cases
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("arm"));
		assertTrue(list.contains("banana"));
		assertTrue(list.contains("pineapple"));
		assertFalse(list.contains("aple"));
		assertFalse(list.contains("ram"));
		assertFalse(list.contains("banan"));
		assertFalse(list.contains("applepine"));
		assertFalse(list.contains(null));
		
	}
	
	/**
	 * Test that equality can be checked between two lists; tests are included for
	 * equal and not equal lists.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		//Make two lists the same and one list different
		// Lists 1 and 2 are the same, 3 is different
		list1.add("apple");
		list1.add("arm");
		list1.add("banana");
		list1.add("pineapple");
		
		list2.add("apple");
		list2.add("arm");
		list2.add("banana");
		list2.add("pineapple");
		
		list3.add("pear");
		list3.add("orange");
		
		//Test for equality and non-equality
		assertEquals(list1, list2);
		assertNotEquals(list1, list3);
		assertNotEquals(list2, list3);
	}
	
	/**
	 * Tests that hash codes are unique for different lists.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		//Make two lists the same and one list different
		// Lists 1 and 2 are the same, 3 is different
		list1.add("apple");
		list1.add("arm");
		list1.add("banana");
		list1.add("pineapple");
		
		list2.add("apple");
		list2.add("arm");
		list2.add("banana");
		list2.add("pineapple");
		
		list3.add("pear");
		list3.add("orange");
		
		//Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}

}
 