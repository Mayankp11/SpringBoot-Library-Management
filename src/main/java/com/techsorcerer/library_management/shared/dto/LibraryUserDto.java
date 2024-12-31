package com.techsorcerer.library_management.shared.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class LibraryUserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDateTime dateOfMembership;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public LocalDateTime getDateOfMembership() {
		return dateOfMembership;
	}

	public void setDateOfMembership(LocalDateTime dateOfMembership) {
		this.dateOfMembership = dateOfMembership;
	}

}
