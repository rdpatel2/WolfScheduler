package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Tests for the Activity class
 * 
 * @author Brendon Hablutzel
 */
class ActivityTest {

	/**
	 * Tests the checkConflict method in the Activity class, specifically for valid cases
	 * @throws InvalidTransitionException For a thrown excpetion with course construction.
	 */
	@Test
	void testCheckConflict() throws InvalidTransitionException {
		//Two non-conflicting Activity objects
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "TH", 1330, 1445);
		
		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
		
		Activity a3 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "A", 0, 0);
		Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "A", 0, 0);
		
		assertDoesNotThrow(() -> a3.checkConflict(a4));
		assertDoesNotThrow(() -> a4.checkConflict(a3));
	}
	
	/**
	 * Tests the checkConflict method in the Activity class, specifically for invalid cases
	 * @throws InvalidTransitionException For a thrown excpetion with course construction.
	 */
	@Test
	public void testCheckConflictWithConflict() throws InvalidTransitionException {
		//one day conflicts
	    Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "M", 1330, 1445);
		
	    Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
	    assertEquals("Schedule conflict.", e1.getMessage());
		
	    Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
	    assertEquals("Schedule conflict.", e2.getMessage());
	    
	    //start time of one is end time of other
	    Activity a3 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "MW", 1300, 1400);
	    Activity a4 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "W", 1400, 1500);
	    
	    Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
	    assertEquals("Schedule conflict.", e3.getMessage());
	    
	    Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
	    assertEquals("Schedule conflict.", e4.getMessage());
	    
	    //non-exact overlap
	    Activity a5 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "MWF", 1300, 1400);
	    Activity a6 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "WF", 1330, 1430);
	    
	    Exception e5 = assertThrows(ConflictException.class, () -> a5.checkConflict(a6));
	    assertEquals("Schedule conflict.", e5.getMessage());
	    
	    Exception e6 = assertThrows(ConflictException.class, () -> a6.checkConflict(a5));
	    assertEquals("Schedule conflict.", e6.getMessage());
	    
	    //one entirely contains the other
	    Activity a7 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "MWF", 1230, 1400);
	    Activity a8 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "sesmith5", 100, "WF", 1300, 1330);
	    
	    Exception e7 = assertThrows(ConflictException.class, () -> a7.checkConflict(a8));
	    assertEquals("Schedule conflict.", e7.getMessage());
	    
	    Exception e8 = assertThrows(ConflictException.class, () -> a8.checkConflict(a7));
	    assertEquals("Schedule conflict.", e8.getMessage());
	}
}
