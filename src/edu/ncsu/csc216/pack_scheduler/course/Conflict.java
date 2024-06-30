package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents a conflict between Activities, e.g. when they occur on the same day
 * at overlapping times. 
 * 
 * @author Brendon Hablutzel
 */
public interface Conflict {
	/**
	 * Checks whether a given Activity conflicts with the current Activity. This means
	 * that there exists at least one day where the given Activity occurs at the same time
	 * as the current Activity.
	 * 
	 * @param possibleConflictingActivity the given Activity that may conflict with the current Activity
	 * @throws ConflictException if there is a conflict between the given Activity and the current Activity
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
