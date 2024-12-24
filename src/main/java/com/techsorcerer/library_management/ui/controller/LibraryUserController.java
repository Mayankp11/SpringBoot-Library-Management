package com.techsorcerer.library_management.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("library")
public class LibraryUserController {
	
	@PostMapping
	public String addLibraryUser() {
		return "user is added";
	}
	
	@GetMapping
	public String getUser() {
		return "user details";
	}
	
	@GetMapping
	public String getUserById() {
		return "return user by user id";
	}
	
	public String updateUser() {
		return "update user";
	}
	
	public String deleteUser() {
		return "delete user";
	}

}
