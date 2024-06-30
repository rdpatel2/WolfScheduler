package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.File;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files and writes a set of CourseRecords to a file.
 * These text files contain one course per line, with information about each course
 * separated by commas.
 * 
 * @author Brendon Hablutzel
 */
public class CourseRecordIO {
	
	/**
	 * Faculty Directory to check if faculty exists
	 */
	private static FacultyDirectory facultyDirectory = new FacultyDirectory();
	
	/**
     * Reads course records from a file and generates a list of valid Courses. Any invalid
     * Courses are ignored. If the file to read cannot be found or the permissions are incorrect
     * a FileNotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		
		while(fileReader.hasNextLine()) {
			try {
				// Attempt to read the line, process it in readCourse, and get the object:
				
				Course course = readCourse(fileReader.nextLine());
				
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course current = courses.get(i);
					
					// Check if the current course name and section matches one
					// that has already been processed
					if (course.getName().equals(current.getName()) &&
							course.getSection().equals(current.getSection())) {
						duplicate = true;
						break;
					}
				}
				
				if(!duplicate) {
					courses.add(course);
				}
				// Ignore the course if it is a duplicate
				
			} catch (IllegalArgumentException e) {
				// If the line is invalid, skip it
			}
		}

		// Close the scanner, we are done with reading the file
		fileReader.close();
		RegistrationManager.getInstance();
		return courses;
	}

	/**
	 * Generates a Course object from a String of comma-separated values.
	 * @param nextLine the line String to parse into a Course
	 * @return Course a course generated from the nextLine
	 * @throws IllegalArgumentException if there is any problem parsing the nextLine into a Course, such as
	 * too few or too many tokens being provided, or a startTime and endTime being provided for arranged classes
	 */
	private static Course readCourse(String nextLine) {
		Scanner lineReader = new Scanner(nextLine);
		lineReader.useDelimiter(",");
		
		try {
			// line format:
			// name,title,section,creditHours,instructor,meetingDays,startTime,endTime
			// OR
			// name,title,section,creditHours,instructor,"A"
			String name = lineReader.next();
			String title = lineReader.next();
			String section = lineReader.next();
			int creditHours = lineReader.nextInt();
			String instructor = lineReader.next();
			int enrollmentCap = lineReader.nextInt();
			String meetingDays = lineReader.next();
			
			if ("A".equals(meetingDays)) {
				if (lineReader.hasNext()) {
					lineReader.close();
					// Throw exception on excess tokens
					throw new IllegalArgumentException();
				} else {
					lineReader.close();
					Course course = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays);
					if (facultyDirectory.getFacultyById(instructor) != null) {
						course = new Course(name, title, section, creditHours, instructor, enrollmentCap, meetingDays);
						FacultySchedule facultySchedule = facultyDirectory.getFacultyById(instructor).getSchedule();
						facultySchedule.addCourseToSchedule(course);
					}
					RegistrationManager.getInstance();
					return course;
				}
			}
			
			int startTime = lineReader.nextInt();
			int endTime = lineReader.nextInt();
			
			if (lineReader.hasNext()) {
				lineReader.close();
				// Throw exception on excess tokens
				throw new IllegalArgumentException();
			}
			
			lineReader.close();
			Course course = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDays, startTime, endTime);
			if (facultyDirectory.getFacultyById(instructor) != null) {
				course = new Course(name, title, section, creditHours, instructor, enrollmentCap, meetingDays, startTime, endTime);
				FacultySchedule facultySchedule = facultyDirectory.getFacultyById(instructor).getSchedule();
				facultySchedule.addCourseToSchedule(course);
			}
			RegistrationManager.getInstance();
			return course;
			
		} catch (Exception e) {
			lineReader.close();
			// Empty exception because an invalid Course will simply be ignored by readCourseRecords
			throw new IllegalArgumentException();
		}
	}

	/**
     * Writes the given list of Courses to the given file.
     * @param fileName file to write the set of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		
		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}
		
		fileWriter.close();
	}

}