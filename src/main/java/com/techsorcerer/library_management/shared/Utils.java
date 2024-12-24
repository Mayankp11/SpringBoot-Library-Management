package com.techsorcerer.library_management.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
	
	public String generateBookId(int length) {
		return generateRandomString(length);
	}
	public String generateUserId(int length) {
		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(builder);
	}
}
