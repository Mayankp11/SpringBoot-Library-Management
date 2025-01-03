package com.techsorcerer.library_management.service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.exceptions.LibraryUserServiceException;
import com.techsorcerer.library_management.io.entity.LibraryUserEntity;
import com.techsorcerer.library_management.io.repository.LibraryUserRepository;
import com.techsorcerer.library_management.service.LibraryUserService;
import com.techsorcerer.library_management.shared.Utils;
import com.techsorcerer.library_management.shared.dto.LibraryUserDto;
import com.techsorcerer.library_management.ui.model.response.ErrorMessages;



@Service
public class LibraryUserServiceImpl implements LibraryUserService {

	@Autowired
	LibraryUserRepository libraryUserRepository;

	@Autowired
	Utils utils;

	@Override
	public LibraryUserDto createUser(LibraryUserDto libraryUserDto) {

		if (libraryUserRepository.findByEmail(libraryUserDto.getEmail()) != null) {
			throw new RuntimeException("Record already exists");
		}

		ModelMapper modelMapper = new ModelMapper();
		LibraryUserEntity libraryUserEntity = modelMapper.map(libraryUserDto, LibraryUserEntity.class);

		String libraryUserId = utils.generateUserId(30);
		libraryUserEntity.setUserId(libraryUserId);

		if(libraryUserEntity.getDateOfMembership() == null) {
			
			libraryUserEntity.setDateOfMembership(LocalDateTime.now());
		}
		// Auto-assign today's date if `dateOfMembership` is null
//		if (libraryUserEntity.getDateOfMembership() == null) {
//			Date currentDate = new Date(); // Gets the current date and time
//			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); // Format without milliseconds
//			String formattedDate = dateFormat.format(currentDate);
//
//			libraryUserEntity.setDateOfMembership(formattedDate);
//		}
//		libraryUserEntity.setDateOfMembership(new Date());

		LibraryUserEntity storeUserDetails = libraryUserRepository.save(libraryUserEntity);
		LibraryUserDto returnValue = modelMapper.map(storeUserDetails, LibraryUserDto.class);

		return returnValue;
	}

	@Override
	public List<LibraryUserDto> getUsers(int page, int limit) {
		List<LibraryUserDto> returnValue = new ArrayList<>();

		if (page > 0) {
			page = page - 1;
		}

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<LibraryUserEntity> usersPage = libraryUserRepository.findAll(pageableRequest);
		List<LibraryUserEntity> users = usersPage.getContent();

		for (LibraryUserEntity userEntity : users) {
			LibraryUserDto userDto = new LibraryUserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		return returnValue;
	}

	@Override
	public List<LibraryUserDto> getAllUsers() {
		List<LibraryUserDto> returnvalue = new ArrayList<>();

		List<LibraryUserEntity> allusers = (List<LibraryUserEntity>) libraryUserRepository.findAll();
		for (LibraryUserEntity libraryUserEntity : allusers) {
			LibraryUserDto userDto = new LibraryUserDto();
			userDto.setUserId(libraryUserEntity.getUserId());
			userDto.setFirstName(libraryUserEntity.getFirstName());
			userDto.setLastName(libraryUserEntity.getLastName());
			userDto.setEmail(libraryUserEntity.getEmail());
			userDto.setPassword(libraryUserEntity.getPassword());
			userDto.setDateOfMembership(libraryUserEntity.getDateOfMembership());

			returnvalue.add(userDto);
		}
		return returnvalue;
	}

	@Override
	public LibraryUserDto getUserById(String id) {

		LibraryUserEntity userEntity = libraryUserRepository.findByUserId(id);
		if (userEntity == null) {
			throw new LibraryUserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage() + "with id " + id);
		}

		ModelMapper modelMapper = new ModelMapper();
		LibraryUserDto userDto = modelMapper.map(userEntity, LibraryUserDto.class);

		return userDto;
	}

	@Override
	public LibraryUserDto updateUser(String id, LibraryUserDto userDto) throws LibraryUserServiceException {
		ModelMapper modelMapper = new ModelMapper();
		LibraryUserEntity userEntity = libraryUserRepository.findByUserId(id);
		
		if(userEntity == null) {
			throw new LibraryUserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		if (userDto == null) {
			throw new LibraryUserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		if (StringUtils.isNotBlank(userDto.getFirstName())) {
			userEntity.setFirstName(userDto.getFirstName());
		} else if (StringUtils.isBlank(userDto.getFirstName())) {
			throw new LibraryUserServiceException("Firstname is required");			
		}
		
		if (StringUtils.isNotBlank(userDto.getLastName())) {
			userEntity.setLastName(userDto.getLastName());
		} else if (StringUtils.isBlank(userDto.getLastName())) {
			throw new LibraryUserServiceException("Lastname is required");
		}
		
		LibraryUserEntity saveDetails = libraryUserRepository.save(userEntity);
		return modelMapper.map(saveDetails, LibraryUserDto.class);
		
	}

	@Override
	public void deleteUser(String id) {
		LibraryUserEntity libraryUserEntity = libraryUserRepository.findByUserId(id);
		
		libraryUserRepository.delete(libraryUserEntity);
		
	}

	
	}


