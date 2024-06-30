package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * This custom exception that throws when there is an invalid FSM transition
 * @author Evan Gregorius.
 */
public class InvalidTransitionException extends Exception {

	/**
	 * This is the default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the constructor for the InvalidTransitionException.
	 * @param errorMessage The error message.
	 */
	public InvalidTransitionException(String errorMessage) {
		super(errorMessage);
	}
	
	/**
	 * This constructor is the default message for this exception.
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

}

