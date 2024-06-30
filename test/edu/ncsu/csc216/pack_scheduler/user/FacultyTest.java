package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Runs tests against getters, setters, hashCodes, equals, toString, and constructors for the student class
 * 
 * @author Brendon Hablutzel
 * @author Parker Morrison
 */
class FacultyTest {
		
	/**
	 * The Faculty's first name
	 */
	private static final String FIRST_NAME = "Parker";
	
	/**
	 * The Faculty's last name
	 */
	private static final String LAST_NAME = "Brendon";
	
	/**
	 * The Faculty's student ID
	 */
	private static final String ID = "psmorris";
	
	/**
	 * The Faculty's email
	 */
	private static final String EMAIL = "psmorris@ncsu.edu";
	
	/**
	 * The Faculty's hashed password
	 */
	private static final String PASSWORD = "password123";
	
	/**
	 * The maximum number of credits this Faculty can have
	 */
	private static final int MAX_CLASSES = 3;
	
	/**
	 * Tests same and different values for the hashCode
	 */
	@Test
	void testHashCode() {
		Faculty s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s3 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s4 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s5 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD, MAX_CLASSES);
		Faculty s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CLASSES);
		Faculty s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);
		
		//test hash code for same values
		assertEquals(s1.hashCode(), s2.hashCode());
		
		//test hash code for different values
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
	}

	/**
	 * Tests long constructor
	 */
	@Test
	void testStudentStringStringStringStringStringInt() {
		Faculty s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test for a valid Faculty
		try {
			assertEquals(FIRST_NAME, s1.getFirstName());
			assertEquals(LAST_NAME, s1.getLastName());
			assertEquals(ID, s1.getId());
			assertEquals(EMAIL, s1.getEmail());
			assertEquals(PASSWORD, s1.getPassword());
			assertEquals(MAX_CLASSES, s1.getMaxCourses());
		} catch(IllegalArgumentException e) {
			fail();
		}
		
	    //test invalid first name
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid first name", e1.getMessage());

		//test invalid last name
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid last name", e2.getMessage());

		//test null id
		Exception e3 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid id", e3.getMessage());
		
		//test empty id
		Exception e4 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid id", e4.getMessage());		

		//test invalid email
		Exception e5 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid email", e5.getMessage());
		
		//test invalid password
		Exception e6 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_CLASSES));
		assertEquals("Invalid password", e6.getMessage());

		//test invalid max classes
		Exception e7 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 4));
		assertEquals("Invalid max courses", e7.getMessage());
	}

	/**
	 * Tests short constructor
	 */
	@Test
	void testStudentStringStringStringStringString() {
		Faculty s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		try {
			assertEquals(FIRST_NAME, s1.getFirstName());
			assertEquals(LAST_NAME, s1.getLastName());
			assertEquals(ID, s1.getId());
			assertEquals(EMAIL, s1.getEmail());
			assertEquals(PASSWORD, s1.getPassword());
			assertEquals(3, s1.getMaxCourses());
		} catch(IllegalArgumentException e) {
			fail();
		}
		
	    //test invalid first name
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(null, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid first name", e1.getMessage());

		//test invalid last name
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, null, ID, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid last name", e2.getMessage());

		//test invalid id
		Exception e3 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid id", e3.getMessage());
		
		//test invalid email
		Exception e4 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, ID, null, PASSWORD, MAX_CLASSES));
		assertEquals("Invalid email", e4.getMessage());
		
		//test invalid password
		Exception e5 = assertThrows(IllegalArgumentException.class,
						() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, MAX_CLASSES));
		assertEquals("Invalid password", e5.getMessage());
	}

	/**
	 * Tests the setter for email fails if empty, null, doesn't contain an @, doesn't contain a ., index of the last . character is earlier than the first @ character
	 */
	@Test
	void testSetEmail() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test setting null email
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail(null));
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals(EMAIL, s.getEmail()); //Check that email didn't change
		
		//test setting empty email
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail(""));
		assertEquals("Invalid email", e2.getMessage()); //Check correct exception message
		assertEquals(EMAIL, s.getEmail()); //Check that email didn't change
		
		//test setting null email
		Exception e3 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail("no_at_gmail."));
		assertEquals("Invalid email", e3.getMessage()); //Check correct exception message
		assertEquals(EMAIL, s.getEmail()); //Check that email didn't change
		
		//test setting null email
		Exception e4 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail("no_dot_gmail@"));
		assertEquals("Invalid email", e4.getMessage()); //Check correct exception message
		assertEquals(EMAIL, s.getEmail()); //Check that email didn't change
		
		//test setting null email
		Exception e5 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail("first.last@ncsu"));
		assertEquals("Invalid email", e5.getMessage()); //Check correct exception message
		assertEquals(EMAIL, s.getEmail()); //Check that email didn't change
		
		//test setting valid email
		s.setEmail("student@ncsu.edu");
		assertEquals("student@ncsu.edu", s.getEmail());
	}

	/**
	 * Tests the setter for password fails if empty or null
	 */
	@Test
	void testSetPassword() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test setting null password
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals(PASSWORD, s.getPassword()); //Check that password didn't change
		
		//test setting empty password
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setPassword(""));
		assertEquals("Invalid password", e2.getMessage()); //Check correct exception message
		assertEquals(PASSWORD, s.getPassword()); //Check that password didn't change
		
		
		s.setPassword("password321");
		assertEquals("password321", s.getPassword());
	}

	/**
	 * Tests the setter for max credits fails if it goes below the lower limit or above the upper limit
	 */
	@Test
	void testSetMaxCredits() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test setting invalid lower limit max credits
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setMaxCourses(4));
		assertEquals("Invalid max courses", e1.getMessage()); //Check correct exception message
		assertEquals(MAX_CLASSES, s.getMaxCourses()); //Check that max credits didn't change
		
		//test setting invalid upper limit max credits
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setMaxCourses(19));
		assertEquals("Invalid max courses", e2.getMessage()); //Check correct exception message
		assertEquals(MAX_CLASSES, s.getMaxCourses()); //Check that max credits didn't change
		
		s.setMaxCourses(2);
		assertEquals(2, s.getMaxCourses());
	}

	/**
	 * Tests the setter for first name fails if empty or null
	 */
	@Test
	void testSetFirstName() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test setting null first name
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		assertEquals(FIRST_NAME, s.getFirstName()); //Check that first name didn't change
		
		//test setting empty first name
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setFirstName(""));
		assertEquals("Invalid first name", e2.getMessage()); //Check correct exception message
		assertEquals(FIRST_NAME, s.getFirstName()); //Check that first name didn't change

		//tests valid first name
		s.setFirstName("Student");
		assertEquals("Student", s.getFirstName());

	}

	/**
	 * Tests the setter for last name fails if empty or null
	 */
	@Test
	void testSetLastName() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		//test setting null first name
		Exception e1 = assertThrows(IllegalArgumentException.class,
								() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals(LAST_NAME, s.getLastName()); //Check that last name didn't change
		
		//test setting empty first name
		Exception e2 = assertThrows(IllegalArgumentException.class,
						() -> s.setLastName(""));
		assertEquals("Invalid last name", e2.getMessage()); //Check correct exception message
		assertEquals(LAST_NAME, s.getLastName()); //Check that last name didn't change
		
		
		s.setLastName("Last");
		assertEquals("Last", s.getLastName()); //Check that first name didn't change
	}
	
	@Test
	void testCompareTo() {
		Faculty s1 = new Faculty("Brendon", "Hablutzel", "bdhablut", "bdhablut@ncsu.edu", "password");
		Faculty s2 = new Faculty("Parker", "Morrison", "psmorris", "psmorris@ncsu.edu", "password");
		Faculty s3 = new Faculty("Gustavo", "Zuniga", "gzuniga", "gzuniga@ncsu.edu", "password");
		Faculty s4 = new Faculty("May", "Hablutzel", "mahablut", "mahablut@ncsu.edu", "password");
		Faculty s5 = new Faculty("May", "Hablutzel", "mhablutz", "mhablutz@ncsu.edu", "password");
		
		//Test exception returned for comparing to null
		assertThrows(NullPointerException.class, () -> s1.compareTo(null));
		
		// Test object equals itself
		assertEquals(0, s1.compareTo(s1));
		
		// Test objects before
		assertEquals(-1, s1.compareTo(s2)); // Hablutzel is before Morrison
		assertEquals(-1, s2.compareTo(s3)); // Morrison is before Zuniga
		assertEquals(-1, s1.compareTo(s4)); // Same last name, but Brendon is before May
		assertEquals(-1, s4.compareTo(s5)); // Same first and last name, but mahablut is before mhablutz
		
		
		// Test objects after
		assertEquals(1, s2.compareTo(s1)); // Morrison is after Hablutzel
		assertEquals(1, s3.compareTo(s2)); // Zuniga is after Morrison
		assertEquals(1, s4.compareTo(s1)); // Same last name, but May is after Brendon
		assertEquals(1, s5.compareTo(s4)); // Same first and last name, but mhablutz is after mahablut
	}

	/**
	 * Tests the toString of Faculty
	 */
	@Test
	void testToString() {
		Faculty s = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		
		assertEquals(
				FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + MAX_CLASSES,
				s.toString());
	}

	/**
	 * Tests if two objects of Faculty are the same
	 */
	@Test
	void testEqualsObject() {
		Faculty s1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s3 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s4 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s5 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CLASSES);
		Faculty s6 = new Faculty(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD, MAX_CLASSES);
		Faculty s7 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CLASSES);
		Faculty s8 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 2);
		Faculty s9 = null;
		
		//test equals
		assertEquals(s1, s1);
		assertEquals(s1, s2);
		assertEquals(s2, s1);
		
		//test not equals
		assertNotEquals(s1, s3);
		assertNotEquals(s1, s4);
		assertNotEquals(s1, s5);
		assertNotEquals(s1, s6);
		assertNotEquals(s1, s7);
		assertNotEquals(s1, s8);
		assertNotEquals(s1, s9);
	}

}
