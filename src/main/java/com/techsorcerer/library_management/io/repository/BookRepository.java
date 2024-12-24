package com.techsorcerer.library_management.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.techsorcerer.library_management.io.entity.BookEntity;


public interface BookRepository extends CrudRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long>{
	BookEntity findByTitle(String title);

	BookEntity findByBookId(String bookId);
}
