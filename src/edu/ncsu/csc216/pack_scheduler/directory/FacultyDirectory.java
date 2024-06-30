package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Manages Faculty in a directory that can add and remove Faculty as well as read and write them from a file
 * 
 * @author Parker Morrison
 */
public class FacultyDirectory {

	/** Algorithm used to has password */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/** The LinkedList Faculty will be stored in */
	private LinkedList<Faculty> facultyDirectory;
	
	/**
	 * Constructor for FacultyDirectory that creates a new facultyDirectory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Creates a new facultyDirectory
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Loads a facultyDirectory from a file and sets this facultyDirectory to it
	 * 
	 * @param file the file to read the facultyDirectory from
	 * @throws IllegalArgumentException if the file cannot be read
	 */
	public void loadFacultyFromFile(String file) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(file);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + file);
		}
	}
	
	/**
	 * Adds a Faculty to the facultyDirectory while checking if a Faculty already exists within the
	 * facultyDirectory
	 * 
	 * @param firstName first name of the Faculty
	 * @param lastName last name of the Faculty
	 * @param id id of the Faculty
	 * @param email email of the Faculty
	 * @param password password of the Faculty
	 * @param repeatPassword a repeated password to make sure the same password was inputed
	 * @param maxClasses the maximum number of classes a Faculty can teach
	 * @throws IllegalArgumentException if the passwords do not match, are empty, or null
	 * @return true if the Faculty is successfully added, otherwise, false if the faculty already exists
	 * in facultyDirectory
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, 
			String repeatPassword, int maxClasses) {
		
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		Faculty toAdd = new Faculty(firstName, lastName, id, email, hashPW, maxClasses);
		
		for(Faculty f : facultyDirectory) {
			if(f.getId().equals(toAdd.getId())) {
				return false;
			}
		}
		
		facultyDirectory.add(toAdd);
		return true;
	}
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if the password cannot be hashed
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Removes a Faculty from the facultyDirectory 
	 * 
	 * @param id the ID of the Faculty to remove
	 * @return true if the faculty is removed, otherwise, false
	 */
	public boolean removeFaculty(String id) {
		
		for(int i = 0; i < facultyDirectory.size(); i++) {
			if(facultyDirectory.get(i).getId().equals(id)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the facultyDirectory as a 2D string to be used in the GUI. Collects the first and last names as
	 * well as the ID of the Faculty.
	 * 
	 * @return 2D array of Faculty information 
	 */
	public String[][] getFacultyDirectory() {
		String[][] output = new String[facultyDirectory.size()][3];
		
		for(int i = 0; i < facultyDirectory.size(); i++) {
			output[i][0] = facultyDirectory.get(i).getFirstName();
			output[i][1] = facultyDirectory.get(i).getLastName();
			output[i][2] = facultyDirectory.get(i).getId();
		}
		
		return output;
	}
	
	/**
	 * Saves the Faculty information into a file
	 * 
	 * @param file The file path to save the facultyDirectory to
	 * @throws IllegalArgumentException if the file writer is unable to write to the file
	 */
	public void saveFacultyDirectory(String file){
		try {
			FacultyRecordIO.writeFacultyRecords(file, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + file);
		}
	}
	
	/**
	 * Gets a faculty by comparing their ID
	 * 
	 * @param id the ID to find the faculty by
	 * @return The faculty who's ID matches the given ID
	 */
	public Faculty getFacultyById(String id) {
		for(int i = 0; i < facultyDirectory.size(); i++) {
			if(id.equals(facultyDirectory.get(i).getId())) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
}
