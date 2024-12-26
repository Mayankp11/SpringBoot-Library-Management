package com.techsorcerer.library_management.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.techsorcerer.library_management.io.entity.LibraryUserEntity;

@Repository
public interface LibraryUserRepository extends CrudRepository<LibraryUserEntity, Long>, PagingAndSortingRepository<LibraryUserEntity,Long>{
	
	LibraryUserEntity findByEmail(String email);

	LibraryUserEntity findByUserId(String id);
}
