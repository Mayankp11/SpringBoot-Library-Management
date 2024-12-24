package com.techsorcerer.library_management.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;
import com.techsorcerer.library_management.ui.model.request.LibraryUserRequestModel;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;

@RestController
@RequestMapping("libraryUser")
public class LibraryUserController {
	
	@Autowired
	LibraryUserService libraryUserService;
	
	@PostMapping
	public LibraryUserRest addLibraryUser(@RequestBody LibraryUserRequestModel libraryUserRequestModel) {
		LibraryUserRest returnValue = new LibraryUserRest();
		
		if(libraryUserRequestModel.getFirstName().isEmpty()) {
			throw new NullPointerException("The object is null");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		LibraryUserDto libraryUserDto = modelMapper.map(libraryUserRequestModel, LibraryUserDto.class);
		
		LibraryUserDto createdUser = libraryUserService.createUser(libraryUserDto);
		returnValue = modelMapper.map(createdUser, LibraryUserRest.class);
		
		return returnValue;
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
