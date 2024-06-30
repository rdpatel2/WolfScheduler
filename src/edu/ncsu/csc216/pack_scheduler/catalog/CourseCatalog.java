/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * A catalog that stores a list of Courses that can be edited
 * @author Parker Morrison
 */
public class CourseCatalog {
	/** A catalog of sorted Courses */
	private SortedList<Course> catalog;
	
	/**
	 * constructs CourseCatalog by creating a new course catalog
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}
	
	/**
	 * Creates a new course catalog while replacing the old one
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}
	
	/**
	 * Reads a file and outputs the re
	 * @param fileName name of the file to read
	 * @throws IllegalArgumentException if the file cannot be read
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * adds a Course with the given parameters to the catalog
	 * @param name the name of the course
	 * @param title the title of the course
	 * @param section the section of the course
	 * @param credits the number of credit hours the class is worth
	 * @param instructorId ID of the instructor teaching the course
	 * @param meetingDays Days the course meets
	 * @param startTime Start time of the meeting
	 * @param endTime End time of the meeting
	 * @param enrollmentCap the enrollment cap of the course
	 * @return true if course is successfully added, otherwise, false
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits,
			String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course toAdd = new Course(name, title, section, credits, instructorId, enrollmentCap,
				meetingDays, startTime, endTime);
		
		for(int i = 0; i < catalog.size(); i++) {
			if(toAdd.isDuplicate(catalog.get(i))) {
				
				return false;
			}
		}
		catalog.add(toAdd);
		return true;
		
	}
	
	/**
	 * Removes the course that matches the given variables from the catalog
	 * @param name name of the course to remove
	 * @param section section of the course to remove
	 * @return true if course is successfully removed, otherwise, false
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) &&
					catalog.get(i).getSection().equals(section)) {
				
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets a course from the catalog that matches the given variables
	 * @param name name of the course to pull from the catalog
	 * @param section section of the course to pull from the catalog
	 * @return the desired course from the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) &&
					catalog.get(i).getSection().equals(section)) {
				
				return catalog.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Takes the name, section, and title of the course catalog and puts the variables
	 * into a 2D String array
	 * @return Array of variables from the catalog
	 */
	public String[][] getCourseCatalog(){
		String [][] output = new String[catalog.size()][5];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            output[i][0] = c.getName();
            output[i][1] = c.getSection();
            output[i][2] = c.getTitle();
            output[i][3] = c.getMeetingString();
            output[i][4] = "" + c.getCourseRoll().getOpenSeats();
        }
        return output;
	}
	
	/**
	 * Writes the catalog to a file of the given path
	 * @param fileName path of the file to write to
	 * @throws IllegalArgumentException if the file cannot be written to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch(IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
	
	
}
