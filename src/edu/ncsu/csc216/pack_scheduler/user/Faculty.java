package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * A class representing a Faculty. Stores information about a Faculty, including
 * first name, last name, id, email, hashed password, and max classes.
 * 
 * @author Brendon Hablutzel
 * @author Parker Morrison
 */
public class Faculty extends User implements Comparable<Faculty> {
	
	/**
	 * The maximum number of credits any Faculty can have
	 */
	public static final int MAX_COURSES = 3;
	
	/**
	 * The maximum number of credits this Faculty can have
	 */
	private int maxCourses;
	
	/**
	 * The schedule of classes for this Faculty
	 */
	private FacultySchedule schedule;
	
	

	/**
	 * Creates a new Faculty with the given first name, last name, id, email, hashed password, and max credits
	 * @param firstName the Faculty's firstName
	 * @param lastName the Faculty's lastName
	 * @param id the Faculty's ID
	 * @param email the Faculty's email
	 * @param hashPW the Faculty's hashed password
	 * @param maxCourses the Faculty's max credits
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}
	
	/**
	 * Creates a new Faculty with the given first name, last name, id, email, hashed password, and 18 for max credits
	 * @param firstName the Faculty's firstName
	 * @param lastName the Faculty's lastName
	 * @param id the Faculty's ID
	 * @param email the Faculty's email
	 * @param hashPW the Faculty's hashed password
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, 3);
	}

	/**
	 * Returns the maximum number of credits for the Faculty
	 * @return the maximum number of credits for this Faculty
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the maximum number of credits for this Faculty
	 * @param maxCourses the maxCourses to set
	 * @throws IllegalArgumentException if the parameter is less than 3 or greater than 18
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < 1 || maxCourses > 3) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		
		this.maxCourses = maxCourses;
	}
	/**
	 * Compares this Faculty against the given Faculty.
	 * Specifically, this compares last name, then first name, then unity ID.
	 * @param s the Faculty to compare this object to
	 * @return -1 if this object is less than the given Faculty, 0 if they are equal, 
	 * and 1 if this object is greater.
	 * @throws NullPointerException if the given Faculty object is null
	 */
	public int compareTo(Faculty s) {
		if(s == null) {
			throw new NullPointerException("Null object.");
		}
		
		if(super.getLastName().compareTo(s.getLastName()) != 0) {
			return 0 > super.getLastName().compareTo(s.getLastName()) ? -1 : 1;
		}
		else if(super.getFirstName().compareTo(s.getFirstName()) != 0) {
			return 0 > super.getFirstName().compareTo(s.getFirstName()) ? -1 : 1;
		}
		else if(super.getId().compareTo(s.getId()) != 0) {
			return 0 > super.getId().compareTo(s.getId()) ? -1 : 1;
		}
		else {
			return 0;
		}	
	}
	
	/**
	 * Returns the Faculty's schedule
	 * @return the schedule of classes for this Faculty
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}
	
	
	/**
	 * Returns a comma-separated String containing the first name,last name,id,email, 
	 * hashed password,max credits
	 * @return the comma-separated representation String of the Faculty
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + 
				super.getEmail() + "," + super.getPassword() + "," + maxCourses;
	}
	/**
	 * Returns a hash code of Faculty
	 * @return hash coded Faculty ID
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}
	/**
	 * Checks if 2 Facultys are the same
	 * @param obj the Faculty being compared
	 * @return true if the Facultys are the same false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}
	
	/**
	 * Checks if an instructor is scheduled for more courses than the max
	 * @return true if they are overloaded false if not
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}
	
}
