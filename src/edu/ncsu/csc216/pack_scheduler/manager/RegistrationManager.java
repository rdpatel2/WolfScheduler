package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The registration manager class, containing functionality for logging in,
 * logging out, and enforcing the singleton pattern
 * 
 * @author Brendon Hablutzel
 */
public class RegistrationManager {

	/**
	 * The current instance of RegistrationManager for enforcing singleton
	 */
	private static RegistrationManager instance;

	/**
	 * The catalog of courses
	 */
	private CourseCatalog courseCatalog;

	/**
	 * The directory of students
	 */
	private StudentDirectory studentDirectory;
	
	/** The directory of faculty */
	private FacultyDirectory facultyDirectory;

	/**
	 * The registrar user
	 */
	private User registrar;

	/**
	 * The current user
	 */
	private User currentUser;

	/**
	 * Hashing algorithm
	 */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * The file containing the registrar properties
	 */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * The private constructor for RegistrationManager
	 */
	private RegistrationManager() {
		createRegistrar();
		studentDirectory = new StudentDirectory();
		facultyDirectory = new FacultyDirectory();
		courseCatalog = new CourseCatalog();
	}

	/**
	 * Create a new registrar object
	 * 
	 * @throws IllegalArgumentException if the registrar cannot be created
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Hash the password using the given algorithm
	 * 
	 * @param pw the plain text password to hash
	 * @return the hashed password
	 * @throws IllegalArgumentException if the password cannot be hashed
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Gets the current instance of the RegistrationManager to enforce the singleton
	 * pattern
	 * 
	 * @return the current instance of the RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Gets the course catalog
	 * 
	 * @return the course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Gets the student directory
	 * 
	 * @return the directory of students
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Logs in a user with the given id and password
	 * 
	 * @param id       the user's id
	 * @param password the user's password
	 * @return true if the user could be logged in, false otherwise
	 * @throws IllegalArgumentException if the student was not found in the student directory or if a user
	 * is already logged in
	 */
	public boolean login(String id, String password) {
		String localHashPW = hashPW(password);
		
		if (getCurrentUser() != null) {
			return false;
		}
		
		if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
			currentUser = registrar;
			return true;
		} else if (registrar.getId().equals(id)) {
			// case that the user tried to login as registrar
			return false;
		}
		
		
		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);

		if (s == null && f == null) {
			throw new IllegalArgumentException("User doesn't exist.");
//			return false;
		}
		
		if (s != null && s.getPassword().equals(localHashPW)) {
			currentUser = s;
			return true;
		}
		
		
		if (f != null && f.getPassword().equals(localHashPW)) {
			currentUser = f;
			return true;
		}
		
		
//		throw new IllegalArgumentException("Invalid id and password.");
		return false;
	}

	/**
	 * Logs the current user out
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Returns the current user
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears the course catalog and the student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
	}
	
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if the current user is not a student
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if the current user is not a student
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 * @throws IllegalArgumentException if the current user is not a student
	 */
	public void resetSchedule() {
	    if (!(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Gets and returns the facultyDirectory
	 * 
	 * @return the facultyDirectory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}
	
	/**
	 * Adds a faculty to a course
	 * @param c the course having faculty added to
	 * @param f the faculty member being assigned
	 * @return true if the faculty member is able to remove the course to their schedule
	 * @throws IllegalArgumentException if a non registrar user tries to add faculty to the course
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if(!getCurrentUser().equals(registrar)) {
			throw new IllegalArgumentException("Non Registrar user cannot add faculty to the course");
		}
		
		if (getCurrentUser() == null) {
			return false;
		}
		
		f.getSchedule().addCourseToSchedule(c);
		return true;
	}
	
	/**
	 * removes a faculty to a course
	 * @param c the course having faculty removed from
	 * @param f the faculty member being removed
	 * @return true if the faculty member is able to remove the course to their schedule
	 * @throws IllegalArgumentException if a non registrar user attempts this action
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if(!getCurrentUser().equals(registrar)) {
			throw new IllegalArgumentException("Non Registrar user cannot remove faculty from the course");
		}
		
		if (getCurrentUser() == null) {
			return false;
		}
		
		f.getSchedule().removeCourseFromSchedule(c);
		return true;
	}
	
	/**
	 * Reset's a faculty's schedule
	 * @param f the faculty's schedule to reset
	 * @throws IllegalArgumentException if a non registrar user tries to reset the schedule
	 */
	public void resetFacultySchedule(Faculty f) {
		if (getCurrentUser() != null && getCurrentUser().getId().equals(registrar.getId())) {
			f.getSchedule().resetSchedule();
		} else {
			throw new IllegalArgumentException("Non Registrar user cannot reset schedule");
		}
		
		
	}

	/**
	 * Represents a Registrar in the system. The Registrar can view and edit all schedules.
	 * @author Brendon Hablutzel
	 */
	private static class Registrar extends User {

		/**
		 * Creates a registrar user
		 * 
		 * @param firstName the first name
		 * @param lastName  the last name
		 * @param id        the user's id
		 * @param email     the user's email
		 * @param hashPW    the user's hashed password
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}