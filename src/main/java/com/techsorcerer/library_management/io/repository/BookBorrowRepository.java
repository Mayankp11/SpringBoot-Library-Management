package com.techsorcerer.library_management.io.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techsorcerer.library_management.io.entity.BookBorrowEntity;
import com.techsorcerer.library_management.io.entity.BookEntity;
import com.techsorcerer.library_management.ui.model.response.BookStatus;


@Repository
public interface BookBorrowRepository extends CrudRepository<BookBorrowEntity, Long> {

BookBorrowEntity findByBookIdAndStatus(BookEntity bookEntity,String status );

BookBorrowEntity findByBorrowId(String borrowId);

List<BookBorrowEntity> findByStatus(String status);


	
	
}
