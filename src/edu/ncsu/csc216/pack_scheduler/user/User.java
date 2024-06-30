package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Creates a new user object
 * @author Rohan
 */
public abstract class User {

	/**
	 * The Student's first name
	 */
	private String firstName;
	/**
	 * The Student's last name
	 */
	private String lastName;
	/**
	 * The Student's student ID
	 */
	private String id;
	/**
	 * The Student's email
	 */
	private String email;
	/**
	 * The Student's hashed password
	 */
	private String password;

	/**
	 * Returns the Student's email
	 * @return the email of the Student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the Student
	 * @param email the email to set
	 * @throws IllegalArgumentException if the email is null or an empty String, the email
	 * doesn't contain an '@' character, the email doesn't contain a '.' character, or
	 * the index of the last '.' character in the String is earlier than the index of the
	 * first '@' character.
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		boolean foundAt = false;
		boolean foundPeriod = false;
		
		for (int i = 0; i < email.length(); i++) {
			char currentChar = email.charAt(i);
			
			if (currentChar == '.' && foundAt) {
				foundPeriod = true;
			}
			
			if (currentChar == '@') {
				foundAt = true;
			}
		}
		
		if (!foundAt || !foundPeriod) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		this.email = email;
	}

	/**
	 * Returns the hashed password for the Student
	 * @return the Student's hashed password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the hashed password for the Student
	 * @param password the password to set
	 * @throws IllegalArgumentException if the password is null or an empty string
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		this.password = password;
	}

	/**
	 * Get the first name of the Student
	 * @return the first name of the Student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the Student
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if the last name is null or an empty String
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Get the last name of the Student
	 * @return the last name of the Student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the Student
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if the last name is null or an empty String
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		
		this.lastName = lastName;
	}

	/**
	 * Return the ID of the Student
	 * @return the ID of the Student
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the student
	 * @param id the id to set
	 * @throws IllegalArgumentException if the id is null or an empty String
	 */
	protected void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}
	/**
	 * Genereates a hash code for the user object
	 * @return hashed user
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	/**
	 * Checks if two users are the same
	 * @param obj The user being compared
	 * @return true if they are the same false if else
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	/**
	 * Creates an user object
	 * @param firstName first name of user
	 * @param lastName last name of user
	 * @param id id of user
	 * @param email email of user
	 * @param password password of user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

}