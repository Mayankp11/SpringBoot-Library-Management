package com.techsorcerer.library_management.service;

import java.util.List;

import com.techsorcerer.library_management.shared.dto.LibraryUserDto;

public interface LibraryUserService {

	LibraryUserDto createUser(LibraryUserDto libraryUserDto);

	List<LibraryUserDto> getUsers(int page, int limit);

	List<LibraryUserDto> getAllUsers();

	LibraryUserDto getUserById(String id);

	LibraryUserDto updateUser(String id, LibraryUserDto userDto);

	void deleteUser(String id);

}
