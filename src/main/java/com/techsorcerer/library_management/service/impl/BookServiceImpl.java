package com.techsorcerer.library_management.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.exceptions.BookServiceException;
import com.techsorcerer.library_management.io.entity.BookEntity;
import com.techsorcerer.library_management.io.repository.BookRepository;
import com.techsorcerer.library_management.service.BookService;
import com.techsorcerer.library_management.shared.Utils;
import com.techsorcerer.library_management.shared.dto.BookDto;
import com.techsorcerer.library_management.ui.model.response.ErrorMessages;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	Utils utils;

	@Override
	public BookDto addBook(BookDto book) {
		BookDto returnValue = new BookDto();

		if (bookRepository.findByTitle(book.getTitle()) != null) {
			throw new RuntimeException("Error");
		}

		BookEntity booksEntity = new BookEntity();
		BeanUtils.copyProperties(book, booksEntity);
		String publicBookId = utils.generateBookId(30);
		booksEntity.setBookId(publicBookId);

		BookEntity storeDetails = bookRepository.save(booksEntity);
		BeanUtils.copyProperties(storeDetails, returnValue);

		return returnValue;
	}

	@Override
	public BookDto getBookByBookId(String bookId) {
		BookDto returnValue = new BookDto();
		BookEntity bookEntity = bookRepository.findByBookId(bookId);
		
		if(bookEntity == null) {
			throw new BookServiceException("Book with id "+bookId+" cannot be found");
		}
		
		BeanUtils.copyProperties(bookEntity, returnValue);
		
		return returnValue;
	}

}
