package com.hadican.bildirim.model.notification;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Data {

	@NotEmpty
	private String title;

	@NotEmpty
	private String message;

	public Data() {
	}

	public Data(String title, String message) {
		this.title = title;
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

}
