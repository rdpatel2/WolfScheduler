/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll class
 */
class CourseRollTest {

	/**
	 * tests setting the enrollment cap
	 */
	@Test
	void testSetEnrollmentCap() {
		Course c = new Course("CSC217", "Title", "001", 3, "psmorris", 20, "A");
		
		CourseRoll roll = c.getCourseRoll();
		assertThrows(IllegalArgumentException.class, 
					() -> roll.setEnrollmentCap(300));
		assertThrows(IllegalArgumentException.class, 
				() -> roll.setEnrollmentCap(3));
		roll.setEnrollmentCap(100);
		assertEquals(100, roll.getEnrollmentCap());
		
	}
	
	/**
	 * tests enroll
	 */
	@Test
	void testEnroll() {
		Course c = new Course("CSC217", "Title", "001", 3, "psmorris", 20, "A");
		
		CourseRoll roll = c.getCourseRoll();
		roll.setEnrollmentCap(10);
		assertThrows(IllegalArgumentException.class, 
					() -> roll.enroll(null));
		Student s = new Student("first", "last", "flast", "flast@ncsu.edu", "password", 18);
		roll.enroll(s);
		assertEquals(9, roll.getOpenSeats());
		Student s2 = new Student("first2", "last2", "flast2", "flast2@ncsu.edu", "password", 18);
		assertTrue(roll.canEnroll(s2));
		Student s3 = new Student("first3", "last3", "flast3", "flast3@ncsu.edu", "password", 18);
		Student s4 = new Student("first4", "last4", "flast4", "flast4@ncsu.edu", "password", 18);
		Student s5 = new Student("first5", "last5", "flast5", "flast5@ncsu.edu", "password", 18);
		Student s6 = new Student("first6", "last6", "flast6", "flast6@ncsu.edu", "password", 18);
		Student s7 = new Student("first7", "last7", "flast7", "flast7@ncsu.edu", "password", 18);
		Student s8 = new Student("first8", "last8", "flast8", "flast8@ncsu.edu", "password", 18);
		Student s9 = new Student("first9", "last9", "flast9", "flast9@ncsu.edu", "password", 18);
		Student s10 = new Student("first10", "last10", "flast10", "flast10@ncsu.edu", "password", 18);
		roll.enroll(s2);
		roll.enroll(s3);
		roll.enroll(s4);
		roll.enroll(s5);
		roll.enroll(s6);
		roll.enroll(s7);
		roll.enroll(s8);
		roll.enroll(s9);
		roll.enroll(s10);
		assertFalse(roll.canEnroll(s10));
		Student s11 = new Student("first11", "last11", "flast11", "flast11@ncsu.edu", "password", 18);
		assertTrue(roll.canEnroll(s11));
		roll.enroll(s11);
		assertEquals(1, roll.getNumberOnWaitlist());
		roll.drop(s10);
		assertEquals(0, roll.getNumberOnWaitlist());
		assertEquals(0, roll.getOpenSeats());
		assertFalse(roll.canEnroll(s11));
		
		roll.enroll(s10);
		assertFalse(roll.canEnroll(s10));
		assertEquals(1, roll.getNumberOnWaitlist());
		roll.drop(s10);
		assertEquals(0, roll.getNumberOnWaitlist());
		assertEquals(0, roll.getOpenSeats());
		
		assertThrows(IllegalArgumentException.class, 
				() -> roll.drop(null));
		
		assertFalse(roll.canEnroll(s));
		assertThrows(IllegalArgumentException.class, () -> roll.enroll(s));
	}

}
