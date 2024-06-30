/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Tests CourseCatalog for it's different functions
 * @author Parker Morrison
 */
class CourseCatalogTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC116";
	/** Course title */
	private static final String TITLE = "Intro to Programming - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "jdyoung2";
	/** Course enrollment cap */
	private static final int ENROLLMENT_CAP = 100;
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 910;
	/** Course end time */
	private static final int END_TIME = 1100;
	
	/**
	 * Test method for addCourseToCatalog
	 * @throws InvalidTransitionException For a thrown excpetion with course construction.
	 */
	@Test
	void testAddCourseToCatalog() throws InvalidTransitionException {
		CourseCatalog cc = new CourseCatalog();
		
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertFalse(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		
		String[] course1 = cc.getCourseCatalog()[0];
		assertEquals(NAME, course1[0]);
		assertEquals(SECTION, course1[1]);
		assertEquals(TITLE, course1[2]);
	}
	
	/**
	 * Test method for loadCoursesFromFile
	 */
	@Test
	void testLoadCoursesFromFile() {
		CourseCatalog cc = new CourseCatalog();
		
		//checks for an invalid file
		try {
			cc.loadCoursesFromFile("not-real-file.txt");
			fail();
		} catch(IllegalArgumentException e) {
			assertEquals("Unable to read file not-real-file.txt", e.getMessage());
		}
				
		//checks for a valid file
		cc.loadCoursesFromFile(validTestFile);
		String[] course1 = cc.getCourseCatalog()[0];
		assertEquals(NAME, course1[0]);
		assertEquals(SECTION, course1[1]);
		assertEquals(TITLE, course1[2]);
		
		
	}

	

	/**
	 * Test method for removeCourseFromCatalog
	 */
	@Test
	void testRemoveCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();
		
		// remove a course from an empty schedule
		assertFalse(cc.removeCourseFromCatalog(NAME, SECTION));
		
		//remove courses
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(13, cc.getCourseCatalog().length);
		assertTrue(cc.removeCourseFromCatalog("CSC116", "001"));
		assertEquals(12, cc.getCourseCatalog().length);
		assertTrue(cc.removeCourseFromCatalog("CSC116", "002"));
		assertTrue(cc.removeCourseFromCatalog("CSC116", "003"));
		assertTrue(cc.removeCourseFromCatalog("CSC216", "001"));
		assertEquals(9, cc.getCourseCatalog().length);
		
		
	}

	/**
	 * Test method for getCourseFromCatalog
	 * @throws InvalidTransitionException For a thrown excpetion with course construction.
	 */
	@Test
	void testGetCourseFromCatalog() throws InvalidTransitionException {
		CourseCatalog cc = new CourseCatalog();
		
		//Attempt to get a course that doesn't exist
		assertNull(cc.getCourseFromCatalog("CSC492", "001"));
		
		//Attempt to get a course that does exist
		Course c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(c, cc.getCourseFromCatalog("CSC116", "001"));
	}

	/**
	 * Test method for getCourseCatalog
	 */
	@Test
	void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile(validTestFile);
		
		//Get the catalog and make sure contents are correct
		//Name, section, title
		String [][] catalog = cc.getCourseCatalog();
		//Row 0
		assertEquals("CSC116", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Intro to Programming - Java", catalog[0][2]);
		//Row 1
		assertEquals("CSC116", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Intro to Programming - Java", catalog[1][2]);
		//Row 2
		assertEquals("CSC116", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Intro to Programming - Java", catalog[2][2]);
		//Row 3
		assertEquals("CSC216", catalog[3][0]);
		assertEquals("001", catalog[3][1]);
		assertEquals("Software Development Fundamentals", catalog[3][2]);
		//Row 4
		assertEquals("CSC216", catalog[4][0]);
		assertEquals("002", catalog[4][1]);
		assertEquals("Software Development Fundamentals", catalog[4][2]);
		//Row 5
		assertEquals("CSC216", catalog[5][0]);
		assertEquals("601", catalog[5][1]);
		assertEquals("Software Development Fundamentals", catalog[5][2]);
		//Row 6
		assertEquals("CSC217", catalog[6][0]);
		assertEquals("202", catalog[6][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
		//Row 7
		assertEquals("CSC217", catalog[7][0]);
		assertEquals("211", catalog[7][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
		//Row 8
		assertEquals("CSC217", catalog[8][0]);
		assertEquals("223", catalog[8][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
		//Row 9
		assertEquals("CSC217", catalog[9][0]);
		assertEquals("601", catalog[9][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
		//Row 10
		assertEquals("CSC226", catalog[10][0]);
		assertEquals("001", catalog[10][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
		//Row 11
		assertEquals("CSC230", catalog[11][0]);
		assertEquals("001", catalog[11][1]);
		assertEquals("C and Software Tools", catalog[11][2]);
		//Row 12
		assertEquals("CSC316", catalog[12][0]);
		assertEquals("001", catalog[12][1]);
		assertEquals("Data Structures and Algorithms", catalog[12][2]);
	}

	/**
	 * Test method for saveCourseCatalog
	 * @throws InvalidTransitionException For a thrown excpetion with course construction.
	 */
	@Test
	void testSaveCourseCatalog() throws InvalidTransitionException {
		//tests that empty schedule exports correctly 
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile(invalidTestFile);
		cc.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//add courses and test that exports correctly
		cc = new CourseCatalog();
		assertTrue(cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", 10, "MW", 1250, 1440));
		assertTrue(cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445));
		assertTrue(cc.addCourseToCatalog("CSC216", "Software Development Fundamentals", "601", 3, "jctetter", 10, "A", 0, 0));
		
		cc.saveCourseCatalog("test-files/actual_course_records.txt");
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
