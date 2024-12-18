package com.techsorcerer.library_management.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.io.entity.BookEntity;
import com.techsorcerer.library_management.io.repository.BookRepository;
import com.techsorcerer.library_management.service.BookService;
import com.techsorcerer.library_management.shared.dto.BookDto;


@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	@Override
	public BookDto addBook(BookDto book) {
BookDto returnValue = new BookDto();
		
		if(bookRepository.findByTitle(book.getTitle()) != null) {
			throw new RuntimeException("Error");
		}
		
		BookEntity booksEntity = new BookEntity();
		
		
		BeanUtils.copyProperties(book, booksEntity);
		
		BookEntity storeDetails = bookRepository.save(booksEntity);
		BeanUtils.copyProperties(storeDetails, returnValue);
		
		return returnValue;
	}

}
