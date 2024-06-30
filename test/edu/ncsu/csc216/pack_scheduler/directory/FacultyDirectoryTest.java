package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests FacultyDirectory for it's different functionalities
 */
class FacultyDirectoryTest {
	
	/**
	 * Tests the add and remove methods in FacultyDirectory
	 */
	@Test
	void testAddRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		assertEquals(3, fd.getFacultyDirectory().length);
		
		assertEquals("awitt", fd.getFacultyById("awitt").getId());
		assertEquals("fmeadow", fd.getFacultyById("fmeadow").getId());
		assertEquals("bbrewer", fd.getFacultyById("bbrewer").getId());
		
		assertFalse(fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2));
		assertEquals(3, fd.getFacultyDirectory().length);
		
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", null, "pw", 2));
		assertEquals("Invalid password", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", null, 2));
		assertEquals("Invalid password", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "", "pw", 2));
		assertEquals("Invalid password", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "", 2));
		assertEquals("Invalid password", e4.getMessage());
		
		assertEquals(3, fd.getFacultyDirectory().length);
		
		assertTrue(fd.removeFaculty("fmeadow"));
		assertEquals(2, fd.getFacultyDirectory().length);
		
		//assertFalse(fd.removeFaculty("fmeadow"));
		assertEquals(2, fd.getFacultyDirectory().length);
		
	}
	
	
	/**
	 * Tests the loadFacultyFromFile method in Faculty Directory
	 */
	@Test
	void testLoadFacultyFromFile() {
		FacultyDirectory fd = new FacultyDirectory();
		String[][] faculty = {
				{"Ashely", "Witt", "awitt"},
				{"Fiona", "Meadows", "fmeadow"},
				{"Brent", "Brewer", "bbrewer"},
				{"Halla", "Aguirre", "haguirr"},
				{"Kevyn", "Patel", "kpatel"},
				{"Elton", "Briggs", "ebriggs"},
				{"Norman", "Brady", "nbrady"},
				{"Lacey", "Walls", "lwalls"}};
		
		
		
		fd.loadFacultyFromFile("test-files/faculty_records.txt");
		assertEquals(8, fd.getFacultyDirectory().length);
		for(int i = 0; i < faculty.length; i++) {
			for(int j = 0; j < faculty[i].length; j++) {
				//assertEquals(faculty[i][j], fd.getFacultyDirectory()[i][j]);
			}
		}
	}
	
	/**
	 * Tests the saveFacultyDirectory method in FacultyDirectory
	 */
	@Test
	void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		
		fd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		fd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		fd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		
		assertEquals(3, fd.getFacultyDirectory().length);
		
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		
		//checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
		
		
	}

}
