package com.techsorcerer.library_management.io.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "library_users")
public class LibraryUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	private Long id;
	
	
	@Column(nullable = false, length = 50, unique = true)
	private String userId;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, length = 100, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private String dateOfMembership;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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

	public String getDateOfMembership() {
		return dateOfMembership;
	}

	public void setDateOfMembership(String formattedDate) {
		this.dateOfMembership = formattedDate;
	}

	

}
