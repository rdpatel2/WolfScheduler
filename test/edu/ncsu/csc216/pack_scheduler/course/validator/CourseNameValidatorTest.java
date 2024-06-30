/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Course Name Validator FSM
 * 
 * @author Rohan Patel
 */
class CourseNameValidatorTest {

	/**
	 * A valid course name
	 */
	private String validCourse1 = "CSC116";

	/**
	 * A valid course name
	 */
	private String validCourse2 = "CSCA116";

	/**
	 * A valid course name
	 */
	private String validCourse3 = "E101";

	/**
	 * A valid course name
	 */
	private String validCourse4 = "MA242";

	/**
	 * A valid course name
	 */
	private String validCourse5 = "CSC116P";

	/**
	 * An invalid course name with only letters
	 */
	private String invalidCourse1 = "CSCSC";

	/**
	 * An invalid course name with too many digits
	 */
	private String invalidCourse2 = "CSC116O1";

	/**
	 * An invalid course name with too many digits
	 */
	private String invalidCourse3 = "CSC1166";

	/**
	 * An invalid course name with too many letters
	 */
	private String invalidCourse4 = "CSCAS116";

	/**
	 * The FSM to use for the tests
	 */
	private CourseNameValidator fsm;

	/**
	 * Set up method to be run before every test
	 */
	@BeforeEach
	void setUp() throws Exception {
		fsm = new CourseNameValidator();
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM#isValid(java.lang.String)}.
	 */
	@Test
	void testIsValid() {
		try {
			assertTrue(fsm.isValid(validCourse1));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(fsm.isValid(validCourse2));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(fsm.isValid(validCourse3));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(fsm.isValid(validCourse4));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(fsm.isValid(validCourse5));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		try {
			assertTrue(fsm.isValid("MA141"));
		} catch (InvalidTransitionException e) {
			fail(e.getMessage());
		}
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid(invalidCourse1));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid(invalidCourse2));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid(invalidCourse3));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid(invalidCourse4));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid("!&^*$"));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid("12CSG"));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid("CSC1C"));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid("CSC12C"));
		assertThrows(InvalidTransitionException.class, () -> fsm.isValid("CSC123CC"));

	}
	
	/**
	 * Tests default constructor for InvalidTransitionException
	 */
	@Test
    public void testDefaultConstructor() {
        InvalidTransitionException exception = new InvalidTransitionException();
        assertEquals("Invalid FSM Transition.", exception.getMessage());
    }

}
