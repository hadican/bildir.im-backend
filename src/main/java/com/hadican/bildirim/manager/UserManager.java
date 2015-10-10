package com.hadican.bildirim.manager;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hadican.bildirim.model.registration.User;
import com.hadican.bildirim.util.PasswordEncoder;

public class UserManager {

	private final Datastore userDatastore;
	private final PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserManager.class);

	public UserManager(Datastore userDatastore) throws NoSuchAlgorithmException {
		this.userDatastore = userDatastore;
		this.passwordEncoder = new PasswordEncoder();
	}

	/**
	 * Saves {@code user} to the database. <br />
	 * Returns {@code true} if successful.
	 * 
	 * @param user
	 * @return
	 */
	public boolean saveUser(User user) {
		// encrypt password
		String encryptedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		// transform email to lower case
		String lowerCaseEmail = user.getEmail().toLowerCase();
		user.setEmail(lowerCaseEmail);
		// save it
		userDatastore.save(user);
		return true;
	}

	/**
	 * Finds user with provided e-mail and password. <br />
	 * Returns {@code null} if there is no perfect match.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User findUser(String email, String password) {

		// encrypt password
		String encryptedPassword = passwordEncoder.encode(password);

		// find the user
		Query<User> userQuery = userDatastore.createQuery(User.class);
		userQuery.field("email").equal(email.toLowerCase());
		userQuery.field("password").equal(encryptedPassword);
		List<User> userList = userQuery.asList();

		// perfect match
		if (userList.size() == 1) {
			return userList.get(0);
		}

		// well, this is unexpected
		logger.warn("Something going wrong with user: {}", email);
		return null;
	}

	/**
	 * Finds users with provided {@code property} and matching {@code value}
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public List<User> findUsers(String property, String value) {
		return userDatastore.find(User.class, property, value).asList();
	}

}
