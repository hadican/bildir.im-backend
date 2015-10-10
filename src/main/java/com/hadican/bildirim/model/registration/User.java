package com.hadican.bildirim.model.registration;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	public User() {
		// empty constructor for JSON deserialization
	}

	@Id
	private ObjectId id;

	private String email;
	private String password;
	private String gcmRegistrationId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGcmRegistrationId() {
		return gcmRegistrationId;
	}

	public void setGcmRegistrationId(String gcmRegistrationId) {
		this.gcmRegistrationId = gcmRegistrationId;
	}

}
