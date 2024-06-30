/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;



/**
 * Creates a schedule of courses that a student is taking and allows planning around the schedule
 * @author Rohan Patel
 */
public class Schedule {
	
	/**
	 * Private arraylist of courses
	 */
	private ArrayList<Course> schedule;
	/**
	 * Private string state to store title of schedule
	 */
	private String title;
	
	/**
	 * Creates a new schedule object
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 * Sets title of schedule
	 * @param title of schedule
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * Gets title of schedule
	 * @return title of schedule
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Adds course to schedule
	 * @param c course being added
	 * @throws IllegalArgumentException if course is already in schedule, or if course is a conflicting activity
	 * @throws NullPointerException if the course to add is null
	 * @return true if course is added false if not
	 */
	public Boolean addCourseToSchedule(Course c) {
		if (c == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
			try {
				c.checkConflict(schedule.get(i));
			}
			catch (ConflictException e){
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		
		return schedule.add(c);
	}
	
	/**
	 * Removes course from schedule
	 * @param c course being removed
	 * @return true if course is removed, false if not
	 */
	public Boolean removeCourseFromSchedule(Course c) {
		if (c == null) {
			return false;
		}
		
		return schedule.remove(c);
	}
	
	/**
	 * Clears schedule arrayList
	 */
	public void resetSchedule() {
		schedule.clear();
		setTitle("My Schedule");
	} 
	
	/**
	 * gets 2D array of scheduled courses including
	 * course name, section, and title
	 * @return 2D array of scheduled courses
	 */
	public String[][] getScheduledCourses() {
		if (schedule == null) {
			return new String[0][0]; // Return an empty 2D array if the catalog is empty
		}

		String [][] scheduleArray = new String[schedule.size()][4];
        for (int i = 0; i < schedule.size(); i++) {
            Course c = schedule.get(i);
            scheduleArray[i] = c.getShortDisplayArray();
        }
        return scheduleArray;
	}
	
	/**
	 * Gets the total number of credits in a schedule, and returns them as an integer.
	 * @return The total number of credits in a schedule.
	 */
	public int getScheduleCredits() {
		int totalCredits = 0;
		for (int i = 0; i < schedule.size(); i++) {
            Course c = schedule.get(i);
            totalCredits += c.getCredits();
        }
		return totalCredits;
	}
	
	/**
	 * Checks if a course can be added to the schedule.
	 * @param c The course to be checked if it can be added.
	 * @return Boolean if the course can be added or not.
	 */
	public boolean canAdd(Course c) {
		if (c == null || checkConflict(c, schedule)) {
			return false;
		}
		
		for (Course course : schedule) {
			if (c.getName().equals(course.getName())) {
				return false;
			}
		}
		
		return true;
		
	}
	
	/**
	 * This helper method compares the current instance with a
	 * possible conflicting course to see if there is a conflict.
	 * @param possibleConflictingCourse This is the possible conflicting course.
	 * @param schedule The schedule to be searched through for a conflict.
	 * @return Boolean if there is a schedule conflict.
	 */
	private boolean checkConflict(Course possibleConflictingCourse, ArrayList<Course> schedule) {
		boolean conflict = false;
		for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getStartTime() >= possibleConflictingCourse.getStartTime() && schedule.get(i).getStartTime() <= possibleConflictingCourse.getEndTime() 
    				|| schedule.get(i).getEndTime() >= possibleConflictingCourse.getStartTime() && schedule.get(i).getEndTime() < possibleConflictingCourse.getEndTime() 
    				|| schedule.get(i).getStartTime() < possibleConflictingCourse.getStartTime() && schedule.get(i).getEndTime() >= possibleConflictingCourse.getEndTime()) {
    			for (int j = 0; j < possibleConflictingCourse.getMeetingDays().length(); j++) {
    				if (!(schedule.get(i).getMeetingDays().indexOf(possibleConflictingCourse.getMeetingDays().charAt(j)) == -1) && !(schedule.get(i).getMeetingDays().equals("A"))) {
    						conflict = true;
    				}
    			}
    		}
        }
		return conflict;
	}
	
	
}
