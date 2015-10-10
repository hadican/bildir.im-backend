package com.hadican.bildirim.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.hadican.bildirim.manager.UserManager;
import com.hadican.bildirim.model.registration.User;

@Path("/login")
public class LoginResource {

	private final UserManager userManager;

	public LoginResource(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * Returns GCM registration ID of user from data store. <br />
	 * Returns {@code null} for not found user.
	 * 
	 * @param user
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String login(User user) {

		// find the user with provided email and password
		User foundedUser = userManager.findUser(user.getEmail(), user.getPassword());

		// return null if user is not found.
		if (foundedUser == null) {
			return null;
		}

		// just return GCM registration ID
		return foundedUser.getGcmRegistrationId();
	}

}
