package com.hadican.bildirim.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncoder {

	private MessageDigest digester;

	private static final String ALGORITHM = "YOUR_ALGORITHM";
	private static final byte[] SALT = "YOUR_SALT".getBytes(StandardCharsets.UTF_8);

	public PasswordEncoder() throws NoSuchAlgorithmException {
		digester = MessageDigest.getInstance(ALGORITHM);
	}

	public String encode(String password) {

		byte[] result = null;
		byte[] passwordInBytes = password.getBytes(StandardCharsets.UTF_8);

		synchronized (digester) {
			digester.reset();
			result = digester.digest(passwordInBytes);
			digester.update(SALT);
			result = digester.digest(result);
		}

		return new String(Base64.encodeBase64(result));
	}
}
