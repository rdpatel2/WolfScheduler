/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class
 * @author Rohan Patel
 */
public class ScheduleTest {
	
	
	/**
	 * Tests the schedule constructor
	 */
	@Test
	public void testSchedule() {
		Schedule s = new Schedule();
		assertEquals("My Schedule", s.getTitle());
	}
	
	/**
	 * Tests the addCourseToSchedule method
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule s = new Schedule();
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
		Course c1 = new Course("CSC116", "Introduction to java", "001", 3, "spbalik", 100, "MW", 1330, 1445);
		assertTrue(s.addCourseToSchedule(c));
		Exception e1 = assertThrows(IllegalArgumentException.class, 
					() -> s.addCourseToSchedule(c));
		assertEquals("You are already enrolled in CSC216", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class, 
					() -> s.addCourseToSchedule(c1));
		assertEquals("The course cannot be added due to a conflict.", e2.getMessage());
	}
	
	/**
	 * Tests the removeCourseFromSchedule method
	 */
	@Test 
	public void testRemoveCourseFromSchedule() {
		Schedule s = new Schedule();
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
		Course c1 = new Course("CSC116", "Introduction to java", "001", 3, "spbalik", 100, "TH", 1330, 1445);
		s.addCourseToSchedule(c);
		s.addCourseToSchedule(c1);
		assertTrue(s.removeCourseFromSchedule(c1));
	}
	
	/**
	 * Tests the resetSchedule() method
	 */
	@Test
	public void testResetSchedule() {
		Schedule s = new Schedule();
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
		Course c1 = new Course("CSC116", "Introduction to java", "001", 3, "spbalik", 100, "TH", 1330, 1445);
		s.addCourseToSchedule(c);
		s.addCourseToSchedule(c1);
		s.setTitle("Awesome Schedule");
		assertEquals("Awesome Schedule", s.getTitle());
		s.resetSchedule();
		assertEquals("My Schedule", s.getTitle());
	}
	
	/**
	 * Test WolfScheduler.getScheduledCourses().
	 */
	@Test
	public void testGetScheduledCourses() {
		Schedule s = new Schedule();
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 100, "MW", 1330, 1445);
		Course c1 = new Course("CSC116", "Introduction to java", "001", 3, "spbalik", 100, "TH", 1330, 1445);
		
		//Add some courses and get schedule
		//Name, section, title
		assertTrue(s.addCourseToSchedule(c));
		assertTrue(s.addCourseToSchedule(c1));
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> s.addCourseToSchedule(c1));
	    assertEquals("You are already enrolled in CSC116", e1.getMessage());
		
		String [][] schedule = s.getScheduledCourses();
		//Row 1
		assertEquals("CSC216", schedule[0][0]);
		assertEquals("001", schedule[0][1]);
		assertEquals("Software Development Fundamentals", schedule[0][2]);
		assertEquals("MW 1:30PM-2:45PM", schedule[0][3]);
		assertEquals("100", schedule[0][4]);
		//Row 2
		assertEquals("CSC116", schedule[1][0]);
		assertEquals("001", schedule[1][1]);
		assertEquals("Introduction to java", schedule[1][2]);
		assertEquals("TH 1:30PM-2:45PM", schedule[1][3]);
		assertEquals("100", schedule[1][4]);

	}
	
}
