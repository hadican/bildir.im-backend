package com.hadican.bildirim.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hadican.bildirim.manager.UserManager;
import com.hadican.bildirim.model.registration.User;

@Path("/register")
public class RegistrationResource {

	private final UserManager userManager;

	private static final Logger logger = LoggerFactory.getLogger(RegistrationResource.class);

	public RegistrationResource(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * Registers {@code user} to the database. <br />
	 * Returns {@code false} if anything goes wrong.
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean register(User user) {

		// e-mails are being stored as lower case in DB
		String lowerCaseEmail = user.getEmail().toLowerCase();

		// existing user check
		if (userManager.findUsers("email", lowerCaseEmail).size() > 0) {
			logger.warn("{} is already registered.", lowerCaseEmail);
			return false;
		}

		// save user and return the result
		return userManager.saveUser(user);
	}

}
