package com.hadican.bildirim.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class GCMResponse {

	private byte success;

	public byte getSuccess() {
		return success;
	}

	public void setSuccess(byte success) {
		this.success = success;
	}

}
