package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * A class representing a Student. Stores information about a Student, including
 * first name, last name, id, email, hashed password, and max credits.
 * 
 * @author Brendon Hablutzel
 * @author Parker Morrison
 */
public class Student extends User implements Comparable<Student> {
	
	/**
	 * The maximum number of credits any student can have
	 */
	public static final int MAX_CREDITS = 18;
	
	/**
	 * The maximum number of credits this Student can have
	 */
	private int maxCredits;
	
	/**
	 * The schedule of classes for this student
	 */
	private Schedule schedule;

	/**
	 * Creates a new Student with the given first name, last name, id, email, hashed password, and max credits
	 * @param firstName the Student's firstName
	 * @param lastName the Student's lastName
	 * @param id the Student's ID
	 * @param email the Student's email
	 * @param hashPW the Student's hashed password
	 * @param maxCredits the Student's max credits
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}
	
	/**
	 * Creates a new Student with the given first name, last name, id, email, hashed password, and 18 for max credits
	 * @param firstName the Student's firstName
	 * @param lastName the Student's lastName
	 * @param id the Student's ID
	 * @param email the Student's email
	 * @param hashPW the Student's hashed password
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, 18);
	}

	/**
	 * Returns the maximum number of credits for the Student
	 * @return the maximum number of credits for this Student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum number of credits for this Student
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if the parameter is less than 3 or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		
		this.maxCredits = maxCredits;
	}
	
	/**
	 * Compares this student against the given student.
	 * Specifically, this compares last name, then first name, then unity ID.
	 * @param s the Student to compare this object to
	 * @return -1 if this object is less than the given student, 0 if they are equal, 
	 * and 1 if this object is greater.
	 * @throws NullPointerException if the given student object is null
	 */
	public int compareTo(Student s) {
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
	 * Returns the student's schedule
	 * @return the schedule of classes for this student
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	
	/**
	 * Returns a comma-separated String containing the first name,last name,id,email, 
	 * hashed password,max credits
	 * @return the comma-separated representation String of the Student
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + 
				super.getEmail() + "," + super.getPassword() + "," + maxCredits;
	}
	/**
	 * Returns a hash code of Student
	 * @return hash coded student ID
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}
	/**
	 * Checks if 2 students are the same
	 * @param obj the student being compared
	 * @return true if the students are the same false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * Checks if a course can be added to the schedule.
	 * @param c The course to be checked if it can be added.
	 * @return Boolean if the course can be added or not.
	 */
	public boolean canAdd(Course c) {
		if(!schedule.canAdd(c)) {
			return false;
		}
		else if(schedule.getScheduleCredits() + c.getCredits() > maxCredits) {
			return false;
		}
		return true;
	}

	
}
