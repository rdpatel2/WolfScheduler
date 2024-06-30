package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;


class FacultyRecordIOTest {
	/** a valid file path used for testing */
	private String validFile = "test-files/faculty_records.txt";
	/** an invalid file path used for testing */
	private String invalidFile = "test-files/invalid_faculty_records.txt";
	
	/** Expected results for valid facultys in faculty_records.txt - line 1 */
	private String validFaculty0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,2";
	/** Expected results for valid facultys in faculty_records.txt - line 2 */
	private String validFaculty1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,3";
	/** Expected results for valid facultys in faculty_records.txt - line 3 */
	private String validFaculty2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,1";
	/** Expected results for valid facultys in faculty_records.txt - line 4 */
	private String validFaculty3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,2";
	/** Expected results for valid facultys in faculty_records.txt - line 5 */
	private String validFaculty4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,1";
	/** Expected results for valid facultys in faculty_records.txt - line 6 */
	private String validFaculty5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Expected results for valid facultys in faculty_records.txt - line 7 */
	private String validFaculty6 = "Lane,Berg,lberg,sociis@non.org,pw,1";
	/** Expected results for valid facultys in faculty_records.txt - line 8 */
	private String validFaculty7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,2";
	/** Expected results for valid facultys in faculty_records.txt - line 9 */
	private String validFaculty8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,3";

	/** Array that holds the expected results */
	private String [] validFacultys = {validFaculty3, validFaculty6, validFaculty4, validFaculty5, validFaculty2, validFaculty8,
	        validFaculty0, validFaculty1, validFaculty7};

	/** String that will contain the hashed password for comparison */
	private String hashPW;
	/** The algorithm used to create a hashed password */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up a hashed password "pw" before every test
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validFacultys.length; i++) {
	            validFacultys[i] = validFacultys[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}

	/**
	 * Tests readFacultyRecords for valid and invalid files
	 */
	@Test
	void testReadFacultyRecords() {
		//testing for a valid Faculty Record
		try {
			LinkedList<Faculty> facultys = FacultyRecordIO.readFacultyRecords(validFile);
			assertEquals(8, facultys.size());
			
			assertEquals("Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net," + hashPW + ",2", facultys.get(0).toString());
			
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validFile);
		}
		
		//testing for an invalid Faculty Record
		LinkedList<Faculty> facultys;
		try {
			facultys = FacultyRecordIO.readFacultyRecords(invalidFile);
			assertEquals(0, facultys.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		}
	}

	/**
	 * tests writeFacultyRecords for a valid written file
	 */
	@Test
	void testWriteFacultyRecords() {
		LinkedList<Faculty> facultys  = new LinkedList<Faculty>();
		facultys.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		facultys.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
		facultys.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));
	
		
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", facultys);
		} catch (IOException e) {
			fail("Cannot write to faculty records file");
		}
		
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}
	
	/**
	 * test writeFacultyRecords with no permissions
	 */
	@Test
	public void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> facultys = new LinkedList<Faculty>();
		facultys.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 3));
		
		Exception exception = assertThrows(IOException.class, 
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", facultys));
		assertEquals("/home/sesmith5/actual_faculty_records.txt (No such file or directory)", exception.getMessage());
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
