package com.techsorcerer.library_management.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.io.entity.LibraryUserEntity;
import com.techsorcerer.library_management.io.repository.LibraryUserRepository;
import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;

@Service
public class LibraryUserServiceImpl implements LibraryUserService {
	
	@Autowired
	LibraryUserRepository libraryUserRepository;

	@Override
	public LibraryUserDto createUser(LibraryUserDto libraryUserDto) {
		
		if(libraryUserRepository.findByEmail(libraryUserDto.getEmail()) != null) {			
			throw new RuntimeException("Record already exists");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		LibraryUserEntity libraryUserEntity = modelMapper.map(modelMapper, LibraryUserEntity.class);
		
		LibraryUserEntity storeUserDetails = libraryUserRepository.save(libraryUserEntity);
		LibraryUserDto returnValue = modelMapper.map(storeUserDetails, LibraryUserDto.class);
		
		return returnValue;
	}

}
