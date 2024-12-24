package com.techsorcerer.library_management.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.io.entity.LibraryUserEntity;
import com.techsorcerer.library_management.io.repository.LibraryUserRepository;
import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.Utils;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {
	
	@Autowired
	LibraryUserRepository libraryUserRepository;
	
	@Autowired
	Utils utils;

	@Override
	public LibraryUserDto createUser(LibraryUserDto libraryUserDto) {
		
		if(libraryUserRepository.findByEmail(libraryUserDto.getEmail()) != null) {			
			throw new RuntimeException("Record already exists");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		LibraryUserEntity libraryUserEntity = modelMapper.map(libraryUserDto, LibraryUserEntity.class);
		
		String libraryUserId = utils.generateUserId(30);
		libraryUserEntity.setUserId(libraryUserId);
		
	    // Auto-assign today's date if `dateOfMembership` is null
	    if (libraryUserEntity.getDateOfMembership() == null) {
	    	Date currentDate = new Date(); // Gets the current date and time
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); // Format without milliseconds
	        String formattedDate = dateFormat.format(currentDate);
	        
	        libraryUserEntity.setDateOfMembership(currentDate);
	    }
		
		LibraryUserEntity storeUserDetails = libraryUserRepository.save(libraryUserEntity);
		LibraryUserDto returnValue = modelMapper.map(storeUserDetails, LibraryUserDto.class);
		
		return returnValue;
	}

}
