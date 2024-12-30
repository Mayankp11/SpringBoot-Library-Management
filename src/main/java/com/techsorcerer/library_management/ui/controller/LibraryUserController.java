package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;
import com.techsorcerer.library_management.ui.model.request.LibraryUserRequestModel;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;
import com.techsorcerer.library_management.ui.model.response.OperationStatusModel;
import com.techsorcerer.library_management.ui.model.response.RequestOperationName;
import com.techsorcerer.library_management.ui.model.response.RequestOperationStatus;



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
	
	@GetMapping(produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<LibraryUserRest> getUser(@RequestParam(value = "page", defaultValue = "0")int page,int limit) {
		List<LibraryUserRest> returnValue = new ArrayList<>();
		
		List<LibraryUserDto> getUsers = libraryUserService.getUsers(page, limit);
		
		for(LibraryUserDto userDto : getUsers) {
			LibraryUserRest model = new LibraryUserRest();
			BeanUtils.copyProperties(userDto, model);
			returnValue.add(model);
		}
		
		return returnValue;
	}
	
	@GetMapping(value = "/listUsers",produces = {
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
	
	@PutMapping(path = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public LibraryUserRest updateUser(@PathVariable String id, @RequestBody LibraryUserRequestModel libraryUserRequestModel) {
		
		ModelMapper modelMapper = new ModelMapper();
		
		LibraryUserDto userDto = modelMapper.map(libraryUserRequestModel, LibraryUserDto.class);
		LibraryUserDto updateUser = libraryUserService.updateUser(id, userDto);
		
		LibraryUserRest returnValue = modelMapper.map(updateUser, LibraryUserRest.class); 
		
		return returnValue;
	}
	
	@DeleteMapping(path = "/{id}",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		try {
			libraryUserService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.Success.name());
		} catch (Exception e) {
			 returnValue.setOperationResult(RequestOperationStatus.Error.name());
		        returnValue.setOperationResult("An error occurred while deleting the user.");
		        // You might want to log the exception for further investigation
		    
		}
		
		return returnValue;
	}

}
