package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A checked Exception that occurs when one Activity conflicts with another Activity. That is,
 * there exists at least one day where both Activities occur at the same time.
 * 
 * @author Brendon Hablutzel
 */
public class ConflictException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a ConflictException with the given message.
	 * @param message the message to associate with this ConflictException
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Creates a ConflictException object with the default message: "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}
