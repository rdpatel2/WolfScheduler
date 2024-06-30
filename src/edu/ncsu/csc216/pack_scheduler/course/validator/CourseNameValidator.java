package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * An FSM that validates a course name.
 * 
 * @author Brendon Hablutzel
 */
public class CourseNameValidator {
	/**
	 * The current state of the FSM
	 */
	private State currentState;

	/**
	 * The initial state of the FSM
	 */
	private State stateInitial = new InitialState();

	/**
	 * The letter state of the FSM
	 */
	private State stateLetter = new LetterState();

	/**
	 * The number state of the FSM
	 */
	private State stateNumber = new NumberState();

	/**
	 * The suffix state of the FSM
	 */
	private State stateSuffix = new SuffixState();
	
	/**
	 * The number of letters that the FSM has encountered
	 */
	private int letterCount = 0;
	
	/**
	 * The number of digits that the FSM has encountered
	 */
	private int digitCount = 0;
	
	/**
	 * Whether the FSM is in a valid end state
	 */
	private boolean validEndState = false;

	/**
	 * Determines if a course name is valid
	 * 
	 * @param courseName the course name to validate
	 * @return true if the course name is valid, false if it is not
	 * @throws InvalidTransitionException if the format of the course name is
	 *                                    invalid
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		digitCount = 0;
		letterCount = 0;
		
		currentState = stateInitial;
		for (int i = 0; i < courseName.length(); i++) {
			char c = courseName.charAt(i);

			if (Character.isAlphabetic(c)) {
				currentState.onLetter();
			} else if (Character.isDigit(c)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
		}
		
		return validEndState;
//		return currentState.getStateName().equals("suffix") || (currentState.getStateName().equals("number") && digitCount == 3);
	}

	/**
	 * The abstract class representing a state of the FSM
	 * 
	 * @author Brendon Hablutzel
	 */
	public abstract class State {
		/**
		 * The method handling a letter
		 * 
		 * @throws InvalidTransitionException when the transition is invalid for the
		 *                                    current state
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * The method handling a digit
		 * 
		 * @throws InvalidTransitionException when the transition is invalid for the
		 *                                    current state
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * The method handling any non-alphanumeric character
		 * 
		 * @throws InvalidTransitionException always
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * The initial state of the FSM
	 * 
	 * @author Brendon Hablutzel
	 */
	public class InitialState extends State {
		/**
		 * Handles a letter in the initial state
		 */
		public void onLetter() {
			letterCount++;
			currentState = stateLetter;
		}

		/**
		 * Handles a digit in the initial state
		 * 
		 * @throws InvalidTransitionException always
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}

	/**
	 * The letter state for the FSM
	 * 
	 * @author Brendon Hablutzel
	 */
	public class LetterState extends State {

		/**
		 * The maximum number of allowed prefix letters in a course name
		 */
		private static final int MAX_PREFIX_LETTERS = 4;

		/**
		 * Handles a letter in the letter state
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_PREFIX_LETTERS) {
				letterCount++;
			} else if (letterCount >= MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			} else {
				throw new InvalidTransitionException("Course name must start with a letter.");
			}
		}

		/**
		 * Handles a digit in the letter state
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public void onDigit() throws InvalidTransitionException {
			if (letterCount == 0) {
				throw new InvalidTransitionException("Course name must start with a letter.");
			} else  {
				digitCount++;
				currentState = stateNumber;
			}
		}
	}

	/**
	 * The number state for the FSM
	 * 
	 * @author Brendon Hablutzel
	 */
	public class NumberState extends State {

		/**
		 * The maximum number of numbers to have in a course name
		 */
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
		 * Handles a letter in the number state
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				validEndState = true;
				currentState = stateSuffix;
			} else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		/**
		 * Handles a number in the number state
		 * 
		 * @throws InvalidTransitionException if the transition is invalid
		 */
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < COURSE_NUMBER_LENGTH) {
				digitCount++;
				if (digitCount == COURSE_NUMBER_LENGTH) {
					validEndState = true;
				}
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}

	/**
	 * The suffix state of the FSM
	 * 
	 * @author Brendon Hablutzel
	 */
	public class SuffixState extends State {
		/**
		 * Handles a letter in the suffix state
		 * 
		 * @throws InvalidTransitionException always
		 */
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * Handles a digit in the suffix state
		 * 
		 * @throws InvalidTransitionException always
		 */
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
}
