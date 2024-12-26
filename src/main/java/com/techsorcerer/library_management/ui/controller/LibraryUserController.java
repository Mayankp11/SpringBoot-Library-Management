package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;
import com.techsorcerer.library_management.ui.model.request.LibraryUserRequestModel;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@RestController
@RequestMapping("libraryUser")
public class LibraryUserController {
	
	@Autowired
	LibraryUserService libraryUserService;
	
	@PostMapping(consumes = {MediaType.ALL_VALUE}, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
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
	
//	@GetMapping(produces = {
//			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
//	public List<LibraryUserRest> getUser(@RequestParam(value = "page", defaultValue = "0")int page,int limit) {
//		List<LibraryUserRest> returnValue = new ArrayList<>();
//		
//		List<LibraryUserDto> getUsers = libraryUserService.getUsers(page, limit);
//		
//		for(LibraryUserDto userDto : getUsers) {
//			LibraryUserRest model = new LibraryUserRest();
//			BeanUtils.copyProperties(userDto, model);
//			returnValue.add(model);
//		}
//		
//		return returnValue;
//	}
	
	@GetMapping(produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<LibraryUserRest> getAllUsers(){
		List<LibraryUserRest> returnValue = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		List<LibraryUserDto> getAllUsers = libraryUserService.getAllUsers();
		
		  // Convert each DTO to the corresponding Rest model
	    for (LibraryUserDto userDto : getAllUsers) {
	        LibraryUserRest model = new LibraryUserRest();
	        // Correct the mapping line to assign to 'model'
	        mapper.map(userDto, model);
	        returnValue.add(model);
	    }
		
		return returnValue;
	}
	
	
	@GetMapping(path = "/{id}")
	public LibraryUserRest getUserById(@PathVariable String id) {
		LibraryUserRest returnValue = new LibraryUserRest();
		ModelMapper modelMapper = new ModelMapper();
		
		LibraryUserDto userDto = libraryUserService.getUserById(id);
		modelMapper.map(userDto, returnValue);
		
		return returnValue;
	}
	
	public String updateUser() {
		return "update user";
	}
	
	public String deleteUser() {
		return "delete user";
	}

}
