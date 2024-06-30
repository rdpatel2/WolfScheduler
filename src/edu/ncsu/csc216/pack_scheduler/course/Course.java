package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Represents a valid NC State Course that can be modified or read into a formatted string.
 * This class extends the Activity class, and thus implements getShortDisplayArray(),
 * getLongDisplayArray(), and isDuplicate(Activity activity).
 * 
 * @author Brendon Hablutzel
 * @author Evan Gregorius
 */
public class Course extends Activity implements Comparable<Course> {
//	/** The minimum length of name */
//	private static final int MIN_NAME_LENGTH = 5;
//	
//	/** The maximum length of name */
//	private static final int MAX_NAME_LENGTH = 8;
//	
//	/** The minimum number of letters in the Course name */
//	private static final int MIN_LETTER_COUNT = 1;
//	
//	/** The maximum number of letters in the Course name */
//	private static final int MAX_LETTER_COUNT = 4;
//	
//	/** The expected number of digits in the Course name */
//	private static final int DIGIT_COUNT = 3;
	
	/** The length of section */
	private static final int SECTION_LENGTH = 3;
	
	/** The maximum number of credit hours for a Course */
	private static final int MAX_CREDITS = 5;
	
	/** The minimum number of credit hours for a Course */
	private static final int MIN_CREDITS = 1;
	
	/** Course's name */
	private String name;
	/** Course's section */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor's Unity ID */
	private String instructorId;
	/** Course's roll */
	private CourseRoll roll;
	/**
	 * Constructs a Course object with the given values for all fields.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId Course instructor's Unity ID
	 * @param enrollmentCap maximum number of students allowed to enroll in a course
	 * @param meetingDays meeting days for Course as a sequence of characters
	 * @param startTime start time of Course
	 * @param endTime end time of Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		try {
			setName(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		roll = new CourseRoll(this, enrollmentCap);
	    setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	}
	
	/**
	 * Constructs a Course with the given name, title, section, credits, instructorId, 
	 * and meetingDays for courses that are arranged.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId Course instructor's Unity ID
	 * @param enrollmentCap maximum number of students allowed to enroll in a course
	 * @param meetingDays meeting days for Course as a sequence of characters
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}
	
	/**
	 * Returns the Course's name (for example, "CSC 216").
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name.
	 * @param name the name to set
	 * @throws InvalidTransitionException For a thrown exception with course construction.
	 * @throws IllegalArgumentException if the name passed is null, has a length less than 5 or more than 8,
	 * does not contain a space between letter characters and number characters, has less than 1
	 * or more than 4 letter characters, or does not contain exactly three trailing digit characters
	 */
	private void setName(String name) throws InvalidTransitionException {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
		CourseNameValidator validator = new CourseNameValidator();
		
		boolean valid = validator.isValid(name);
		if (valid) {
			this.name = name;	
		} else {
			throw new IllegalArgumentException("Invalid course name.");
		}
//		// ensure name is not null
//		if (name == null) {
//			throw new IllegalArgumentException("Invalid course name.");
//		}
//		
//		// ensure name is not too short or too long
//		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
//			throw new IllegalArgumentException("Invalid course name.");
//		}
//		
//		// ensure name conforms to L[LLL] NNN
//		boolean spaceFound = false;
//		int numLetters = 0;
//		int numDigits = 0;
//		
//		for (int i = 0; i < name.length(); i++) {
//			char currentChar = name.charAt(i);
//			
//			if (!spaceFound) {
//				// no space has been found, we expect letters or a space:
//				if (Character.isLetter(currentChar)) {
//					numLetters++;
//				} else if (currentChar == ' ') {
//					spaceFound = true;
//				} else {
//					throw new IllegalArgumentException("Invalid course name.");
//				}
//			} else {
//				if (Character.isDigit(currentChar)) {
//					numDigits++;
//				} else {
//					throw new IllegalArgumentException("Invalid course name.");
//				}
//			}
//		}
//		
//		if (numLetters < MIN_LETTER_COUNT || numLetters > MAX_LETTER_COUNT) {
//			throw new IllegalArgumentException("Invalid course name.");
//		}
//		
//		if (numDigits != DIGIT_COUNT) {
//			throw new IllegalArgumentException("Invalid course name.");
//		}
	}
	
	/**
	 * Returns the Course's section (for example, "003").
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course's section (for example, "003").
	 * @param section the section to set
	 * @throws IllegalArgumentException if the section number is not exactly three digits
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}
	
	/**
	 * Returns the Course's credit hours.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the Course's credit hours.
	 * @param credits the number of credit hours to set
	 * @throws IllegalArgumentException if credits is out of bounds
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		
		this.credits = credits;
	}
	
	/**
	 * Returns the Course instructor's Unity ID.
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the Course instructor's Unity ID.
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if the instructor id is null or an empty string
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId != null && "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		
		this.instructorId = instructorId;
	}
	
	/**
	 * Implements Activity's abstract getShortDisplayArray(). Returns an array containing
	 * a short version of information about the Course.
	 * @return a String array containing the name, section, title, and meeting string for the course
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] arr = new String[] {getName(), getSection(), getTitle(), getMeetingString(), "" + roll.getOpenSeats()};
		return arr;
	}
	
	/**
	 * Implements Activity's abstract getLongDisplayArray(). Returns an array containing
	 * a long version of information about the Course.
	 * @return a String array containing the name, section, title, credits, instructor ID, meeting string, and and empty string
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] arr = new String[] {getName(), getSection(), getTitle(), Integer.toString(getCredits()), getInstructorId(), getMeetingString(), ""};
		return arr;
	}
	
	/**
	 * Implement's Activity's abstract isDuplicate(Activity activity). Two courses are the same if
	 * they have the same name
	 * @param activity the Activity to check against for duplicate
	 * @return true if the given Activity is a Course with the same name as the current Course
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if(this.getClass() == activity.getClass()) {
			Course otherCourse = (Course) activity;
			return getName().equals(otherCourse.getName()) && getSection().equals(otherCourse.getSection());
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the meeting days and time for a Course. Meeting days can be M, T, W, H, F, or A for
	 * arranged, and times are given as integers in military time.
	 * @param meetingDays the days the Course meets
	 * @param startTime the start time for the Course
	 * @param endTime the end time for the Course
	 * @throws IllegalArgumentException if meetingDays is null or empty, if the class is arranged
	 * but has a nonzero start or end time, if any of the meeting days are duplicate or
	 * invalid, or if the start and end times are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// must check for null and empty here, because some of the following method
		// calls will fail in these places. Cannot rely on common checks in the super implementation
		// of this method because that must be called after, as it performs assignment,
		// which must be done after these checks
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if ("A".equals(meetingDays)) {
			// case class is arranged, start and end time should be 0
			if (startTime != 0 || endTime != 0) {				
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else {
			// case class is not arranged
			// check for duplicates and invalid characters
			boolean onMon = false;
			boolean onTue = false;
			boolean onWed = false;
			boolean onThu = false;
			boolean onFri = false;
			
			for (int i = 0; i < meetingDays.length(); i++) {
				char currentChar = meetingDays.charAt(i);
				
				if (currentChar == 'M' && !onMon) {
					onMon = true;
				} else if (currentChar == 'T' && !onTue) {
					onTue = true;
				} else if (currentChar == 'W' && !onWed) {
					onWed = true;
				} else if (currentChar == 'H' && !onThu) {
					onThu = true;
				} else if (currentChar == 'F' && !onFri) {
					onFri = true;
				} else {
					// catches duplicates and invalid characters
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	
	/**
	 * Gets the roll of the course. Returns a CourseRoll object that is the roll of the current course.
	 * @return The course roll.
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
	
	/**
	 * Compares this course against the given course. Specifically, this compares name and then section
	 * @param c the Course to compare this object to
	 * @return -1 if this object is less than the given course, 0 if they are equal, 
	 * and 1 if this object is greater.
	 * @throws NullPointerException if the given course object is null
	 */
	public int compareTo(Course c) {
		if(c == null) {
			throw new NullPointerException("Null object.");
		}
		
		if(this.name.compareTo(c.getName()) != 0) {
			return 0 > this.name.compareTo(c.getName()) ? -1 : 1;
		}
		else if(this.section.compareTo(c.getSection()) != 0) {
			return 0 > this.section.compareTo(c.getSection()) ? -1 : 1;
		}
		else {
			return 0;
		}	
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits +
	        	"," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + 
	    	instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Generates a hash code for the current Course using all fields
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given object to the current Course, checking if all fields are equal.
	 * @param obj the Object to compare
	 * @return true if the Object is the same as the current Course over all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}
	

}
