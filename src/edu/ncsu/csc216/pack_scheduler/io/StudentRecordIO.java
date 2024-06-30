package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads in student records from files. Writes student records as a file.
 * @author Parker Morrison
 */
public class StudentRecordIO {
	/**
	 * Reads the student record data from the given file
	 * @param fileName name of the file to be read
	 * @return a SortedList of student records read in from the given file 
	 * @throws FileNotFoundException if there is 
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); 
		SortedList<Student> students = new SortedList<Student>(); 
	    
	    while (fileReader.hasNextLine()) { 
	        try {
	        	Student student = processStudent(fileReader.nextLine()); 
	            boolean duplicate = false;
	            
	            for (int i = 0; i < students.size(); i++) {
	            	Student current = students.get(i);
	                if (student.getId().equals(current.getId())) {
	                    duplicate = true;
	                    break;
	                }
	            }
	            
	            if (!duplicate) {
	                students.add(student); 
	            } 
	        } catch (IllegalArgumentException e) {
	        	//skip this line since we couldn't create a course
	        }
	    }
	    fileReader.close();
	    return students;
	}
	
	/**
	 * Writes a given studentDirectory to a file of the given name
	 * @param fileName the name of the file to write the studentDirectory info to 
	 * @param studentDirectory the data that will be written to a file
	 * @throws IOException if the file could not be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

    	for (int i = 0; i < studentDirectory.size(); i++) {
    	    fileWriter.println(studentDirectory.get(i).toString());
    	}

    	fileWriter.close();
        
    }

	/**
	 * Takes a String line from a file and parses it into a Student object.
	 * 
	 * @param line a line of text containing Student data to parse
	 * @return Student the parsed Student object
	 * @throws IllegalArgumentException if there are too many tokens in the line or
	 * the file could not be read.
	 */
	private static Student processStudent(String line) {
    	Scanner scan = new Scanner(line);
    	scan.useDelimiter(",");
    	
    	try {
    		String firstName = scan.next();
    		String lastName = scan.next();
    		String id = scan.next();
    		String email = scan.next();
    		String hashedPassword = scan.next();
    		int maxCredits = scan.nextInt();
    		
    		if(scan.hasNext()) {
    			scan.close();
    			throw new IllegalArgumentException("Too many tokens.");
    		}
    		
    		scan.close();
    		return new Student(firstName, lastName, id, email, hashedPassword, maxCredits);
    		
    	} catch(NoSuchElementException e) {
    		throw new IllegalArgumentException("File reading error.");
    	}
	}
}
