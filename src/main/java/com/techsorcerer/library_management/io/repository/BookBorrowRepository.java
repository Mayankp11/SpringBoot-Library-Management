package com.techsorcerer.library_management.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techsorcerer.library_management.io.entity.BookBorrowEntity;


@Repository
public interface BookBorrowRepository extends CrudRepository<BookBorrowEntity, Long> {
	
	
}
