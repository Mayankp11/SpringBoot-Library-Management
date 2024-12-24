package com.techsorcerer.library_management.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techsorcerer.library_management.io.entity.LibraryUserEntity;

@Repository
public interface LibraryUserRepository extends CrudRepository<LibraryUserEntity, Long>{
	
	LibraryUserEntity findByEmail(String emial);
}
