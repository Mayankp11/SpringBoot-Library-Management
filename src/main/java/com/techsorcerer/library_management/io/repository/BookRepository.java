package com.techsorcerer.library_management.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.techsorcerer.library_management.io.entity.BookEntity;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long>, PagingAndSortingRepository<BookEntity, Long>{
	BookEntity findByTitle(String title);

	BookEntity findByBookId(String bookId);
}
