package com.hadican.bildirim.model.notification;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {

	@JsonProperty
	private Data data;

	@NotEmpty
	@JsonProperty("registration_ids")
	private ArrayList<String> registrationIds;

	public Notification() {
	}

	public Notification(Data data, ArrayList<String> registrationIds) {
		this.data = data;
		this.registrationIds = registrationIds;
	}

	public Data getData() {
		return data;
	}

	public ArrayList<String> getRegistrationIds() {
		return registrationIds;
	}

}
