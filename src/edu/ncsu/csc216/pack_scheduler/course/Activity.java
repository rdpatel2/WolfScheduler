package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents an abstract Activity for the WolfScheduler. Includes getter and setter methods for
 * title, startTime, endTime, and meetingDays, as well as a method for formatting meeting times.
 * Note that classes that implement Activity should have methods getShortDisplayArray(),
 * getLongDisplayArray(), and isDuplicate(activity Activity)..
 * 
 * @author Brendon Hablutzel
 */
public abstract class Activity implements Conflict {
	/** The upper bound (exclusive) for the hour in startTime and endTime */
	private static final int UPPER_HOUR = 24;
	/** The upper bound (exclusive) for the minute in startTime and endTime */
	private static final int UPPER_MINUTE = 60;
	/** Activity's title */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	private int endTime;

	/**
	 * Creates a new Activity, given a title, meetingDays, a startTime, and an endTime.
	 * 
	 * @param title the title of the Activity
	 * @param meetingDays the days on which the Activity takes place
	 * @param startTime the starting time of the Activity
	 * @param endTime the ending time of the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns the Activity's title (for example, "Software Development Fundamentals").
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity's title (for example, "Software Development Fundamentals").
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null or an empty string
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		
		this.title = title;
	}

	/**
	 * Returns the Activity's meeting days (for example, "MWF" for Monday, Wednesday, Friday).
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity's start time (for example, 1015 for 10:15 AM).
	 * @return the start time
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's end time (for example, 1330 for 1:30 PM).
	 * @return the end time
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Determines whether a military time is valid.
	 * @param time an integer representation of a time
	 * @return whether the time is valid
	 */
	private boolean isValidTime(int time) {
		int hour = time / 100;
		int minute = time % 100;
		
		return hour >= 0 && hour < UPPER_HOUR && minute >= 0 && minute < UPPER_MINUTE;
	}

	/**
	 * Sets the meeting days and times for an Activity. Meeting day validity is
	 * Activity type-specific, and is handled by child classes, this method checks
	 * only for time validity.
	 * @param meetingDays the meeting days as a sequence of characters
	 * @param startTime the start time in military time as an integer
	 * @param endTime the end time in military time as an integer
	 * @throws IllegalArgumentException if a start time or end time is an invalid time, or
	 * if end time is less than start time.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// Since meetingDays is checked for validity in child classes, null and empty
		// checks are handled there
		// if (meetingDays == null || meetingDays.length() == 0) {
		//	   throw new IllegalArgumentException("Invalid meeting days and times.");
		// }

		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if (!isValidTime(startTime) || !isValidTime(endTime)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Converts military time to standard time.
	 * @param time the valid military time
	 * @return the standard time (for example, 1:30PM)
	 */
	private String getTimeString(int time) {
		int hours = time / 100;
		int minutes = time % 100;
		
		String standardHours = "";
		String timeSuffix = ""; // "AM" or "PM"
		if (hours == 0) {
			// midnight
			timeSuffix = "AM";
			standardHours = "12";
		} else if (hours < 12) {
			// hours is greater than 0, but less than twelve (morning)
			timeSuffix = "AM";
			standardHours = Integer.toString(hours);
		} else if (hours == 12) {
			// noon
			timeSuffix = "PM";
			standardHours = Integer.toString(hours);
		} else {
			// after noon, hours greater than 12
			timeSuffix = "PM";
			standardHours = Integer.toString(hours - 12);
		}
		
		String standardMinutes = "";
		if (minutes < 10) {
			standardMinutes = "0" + minutes;
		} else {
			standardMinutes = Integer.toString(minutes);
		}
		
		return standardHours + ":" + standardMinutes + timeSuffix;
	}

	/**
	 * Returns a more readable String representation of the meeting days and start and end times.
	 * @return the meeting String (for example, MWF 1:30PM-2:30PM)
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		
		String startTimeStandard = getTimeString(startTime);
		String endTimeStandard = getTimeString(endTime);
		
		return meetingDays + " " + startTimeStandard + "-" + endTimeStandard;
	}
	
	/**
	 * An abstract method for arrays containing shorter information about a class that extends Activity
	 * @return a String array containing basic information about the class
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * An abstract method for arrays containing longer information about a class that extends Activity
	 * @return a String array containing information about the class
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * An abstract method that checks whether this Activity is a duplicate of another
	 * @param activity the other Activity object to check against
	 * @return true if the given Activity is a duplicate of the current Activity
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Checks whether this Activity conflicts with the given Activity. Two Activities conflict if
	 * there is at least one day where the Activities' times overlap.
	 * 
	 * @param possibleConflictingActivity the Activity to check this Activity against for conflicts
	 * @throws ConflictException if the given Activity conflicts with this Activity
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String thisMeetingDays = this.getMeetingDays();
		int thisStartTime = this.getStartTime();
		int thisEndTime = this.getEndTime();
		
		String otherMeetingDays = possibleConflictingActivity.getMeetingDays();
		int otherStartTime = possibleConflictingActivity.getStartTime();
		int otherEndTime = possibleConflictingActivity.getEndTime();
		
		for (int i = 0; i < thisMeetingDays.length(); i++) {
			char thisMeetingDay = thisMeetingDays.charAt(i);
			
			if (thisMeetingDay == 'A') {
				continue;
			}
			
			for (int j = 0; j < otherMeetingDays.length(); j++) {
				char otherMeetingDay = otherMeetingDays.charAt(j);
				
				if (otherMeetingDay == 'A') {
					continue;
				}
				
				//only check conflicting times if the activities meet on the same day
				if (thisMeetingDay == otherMeetingDay) {
					//true if this start time is during other activity
					boolean conflictingStartTime = thisStartTime >= otherStartTime && thisStartTime <= otherEndTime;
					//true if this end time is during other activity
					boolean conflictingEndTime = thisEndTime >= otherStartTime && thisEndTime <= otherEndTime;
					//true if other entirely contains this
					boolean otherContainsThis = otherEndTime >= thisEndTime && otherStartTime <= thisStartTime;
					//true if this entirely contains other
					boolean thisContainsOther = thisEndTime >= otherEndTime && thisStartTime <= otherStartTime;

					if (conflictingStartTime || conflictingEndTime || otherContainsThis || thisContainsOther) {
						throw new ConflictException("Schedule conflict.");
					}
				}
			}
		}
	}

	/**
	 * Generates a hash code for the current Activity using all fields.
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to the current Activity, checking for equality across all fields
	 * @return true if the Object is equal to Activity
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}