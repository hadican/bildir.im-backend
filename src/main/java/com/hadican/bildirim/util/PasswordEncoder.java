package com.hadican.bildirim.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class PasswordEncoder {

	private final MessageDigest digester;
	private final byte[] salt;

	public PasswordEncoder(String salt, String algorithm) throws NoSuchAlgorithmException {
		this.digester = MessageDigest.getInstance(algorithm);
		this.salt = salt.getBytes(StandardCharsets.UTF_8);
	}

	public String encode(String password) {

		byte[] result = null;
		byte[] passwordInBytes = password.getBytes(StandardCharsets.UTF_8);

		synchronized (digester) {
			digester.reset();
			result = digester.digest(passwordInBytes);
			digester.update(salt);
			result = digester.digest(result);
		}

		return new String(Base64.encodeBase64(result));
	}
}
