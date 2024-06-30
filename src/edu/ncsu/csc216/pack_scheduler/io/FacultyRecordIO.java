package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Reads in Faculty records from files. Writes Faculty records as a file.
 * @author Parker Morrison
 */
public class FacultyRecordIO {
	/**
	 * Reads the Faculty record data from the given file
	 * @param fileName name of the file to be read
	 * @return a SortedList of Faculty records read in from the given file 
	 * @throws FileNotFoundException if there is 
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); 
		LinkedList<Faculty> facultys = new LinkedList<Faculty>(); 
	    
	    while (fileReader.hasNextLine()) { 
	        try {
	        	Faculty faculty = processFaculty(fileReader.nextLine());
	            boolean duplicate = false;
	            
	            for (int i = 0; i < facultys.size(); i++) {
	            	Faculty current = facultys.get(i);
	                if (faculty.getId().equals(current.getId())) {
	                    duplicate = true;
	                    break;
	                }
	            }
	            
	            if (!duplicate) {
	                facultys.add(faculty); 
	            } 
	        } catch (IllegalArgumentException e) {
	        	//skip this line since we couldn't create a course
	        }
	    }
	    fileReader.close();
	    return facultys;
	}
	
	/**
	 * Writes a given FacultyDirectory to a file of the given name
	 * @param fileName the name of the file to write the FacultyDirectory info to 
	 * @param facultyDirectory the data that will be written to a file
	 * @throws IOException if the file could not be written to
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < facultyDirectory.size(); i++) {
    	    fileWriter.println(facultyDirectory.get(i).toString());
    	}

    	fileWriter.close();
        
    }

	/**
	 * Takes a String line from a file and parses it into a Faculty object.
	 * 
	 * @param line a line of text containing Faculty data to parse
	 * @return Faculty the parsed Faculty object
	 * @throws IllegalArgumentException if there are too many tokens in the line or
	 * the file could not be read.
	 */
	private static Faculty processFaculty(String line) {
    	Scanner scan = new Scanner(line);
    	scan.useDelimiter(",");
    	
    	try {
    		String firstName = scan.next();
    		String lastName = scan.next();
    		String id = scan.next();
    		String email = scan.next();
    		String hashedPassword = scan.next();
    		int maxClasses = scan.nextInt();
    		
    		if(scan.hasNext()) {
    			scan.close();
    			throw new IllegalArgumentException("Too many tokens.");
    		}
    		
    		scan.close();
    		return new Faculty(firstName, lastName, id, email, hashedPassword, maxClasses);
    		
    	} catch(NoSuchElementException e) {
    		throw new IllegalArgumentException("File reading error.");
    	}
	}
}
