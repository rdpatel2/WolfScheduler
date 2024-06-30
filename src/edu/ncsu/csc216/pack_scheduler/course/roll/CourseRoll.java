/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * Creates a new Course Roll object keeping track of students in a class
 * @author Rohan Patel
 */
public class CourseRoll {
	
	/**
	 * Stores the students showing how many students in each class
	 */
	private LinkedAbstractList<Student> roll;
	
	/**
	 * Max amount of students allowed in a class
	 */
	private int enrollmentCap;
	
	/** Waitlist of students to be put into the roll if slots open */
	private LinkedQueue<Student> waitlist;
	
	/** The course this CourseRoll is tied to */
	private Course course;
	
	/**
	 * Minimum amount of students a class can have
	 */
	private static final int MIN_ENROLLMENT = 10;
	
	/**
	 * Biggest class possible, no classes can have more than 250 students
	 */
	private static final int MAX_ENROLLMENT = 250;
	
	/** The initial size of the waitlist */
	private static final int WAITLIST_SIZE = 10;
	
	
	/**
	 * Creates a new CourseRoll with the given enrollment cap
	 * @param enrollmentCap the enrollment cap
	 * @param c Course to be added to the CourseRoll
	 * @throws IllegalArgumentException if the given Course is null
	 */
	public CourseRoll(Course c, int enrollmentCap) {
		if(c == null) throw new IllegalArgumentException("Course cannot be null.");
		
		course = c;
		roll = new LinkedAbstractList<Student>(MAX_ENROLLMENT);
		setEnrollmentCap(enrollmentCap);
		waitlist = new LinkedQueue<Student>(WAITLIST_SIZE);
	}
	
	/**
	 * Sets the enrollment cap for this roll
	 * @param cap the cap to set
	 * @throws IllegalArgumentException if the cap is less than minimum enrollment or greater than maximum enrollment
	 * or if the cap is less than the number of students in the roll currently
	 */
	public void setEnrollmentCap(int cap) {
		if (cap < MIN_ENROLLMENT || cap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if (cap < roll.size()) {
			throw new IllegalArgumentException();
		}
		
		if (cap - roll.size() >= 0) {
			this.enrollmentCap = cap;
			roll.setCapacity(enrollmentCap);
		}
		
		roll.setCapacity(cap);
	}
	
	/**
	 * Gets the enrollment cap
	 * @return the enrollment cap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Enrolls a student in the roll
	 * @param s the student to enroll
	 * @throws IllegalArgumentException if the student is null or if there are no open seats
	 * or if the student cannot enroll
	 */
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Student is null.");
		}
		
		if(waitlist.size() == WAITLIST_SIZE) {
			throw new IllegalArgumentException("Waitlist is full.");
		}
		
		if (!canEnroll(s)) {
			throw new IllegalArgumentException("Cannot enroll student.");
		}
		
		if (getOpenSeats() == 0) {
			waitlist.enqueue(s);
			return;
		}
		
		try {
			roll.add(s);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Could not add to list.");
		}
	}
	
	/**
	 * Attempts to drop a student from the roll
	 * @param s the student to drop
	 * @throws IllegalArgumentException if the student is null or cannot be removed
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
//		try {
			if(waitlist.contains(s)) {
				LinkedQueue<Student> temp = new LinkedQueue<Student>(enrollmentCap);
				while(!waitlist.isEmpty()) {
					Student tempStudent = waitlist.dequeue();
					if(!tempStudent.equals(s)) {
						temp.enqueue(tempStudent);
					}
				}
				waitlist = temp;
				return;
			}
			roll.remove(s);
			if(getNumberOnWaitlist() > 0) {
				Student tempStudent = waitlist.dequeue();
				roll.add(tempStudent);	
				tempStudent.getSchedule().addCourseToSchedule(course);
			}
//		}
//		catch (Exception e) {
//			throw new IllegalArgumentException();
//		}
	}
	
	/**
	 * Gets the number of open seats
	 * @return the number of open seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Checks whether a student can enroll
	 * @param s the student to check for
	 * @return true if the student can enroll
	 */
	public boolean canEnroll(Student s) {
		if (getOpenSeats() == 0 && getNumberOnWaitlist() == WAITLIST_SIZE) {
			return false;
		} 
		
		if(waitlist.contains(s)) {
			return false;
		}
		
		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i) == s) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Gets the number of students currently on the waitlist
	 * 
	 * @return the number of students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
