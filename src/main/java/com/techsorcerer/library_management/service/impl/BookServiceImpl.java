package com.techsorcerer.library_management.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.startup.UserDatabase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
			throw new BookServiceException("Record already exists");
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

	@Override
	public List<BookDto> getBooks(int page, int limit) {
	
		List<BookDto> returnValue =  new ArrayList<>();
		
		if(page > 0) {
			page = page - 1;
		}
		
		PageRequest pageableRequest =  PageRequest.of(page, limit);
		
		Page<BookEntity> booksPage = bookRepository.findAll(pageableRequest);
		List<BookEntity> books = booksPage.getContent();
		
		for(BookEntity bookEntity : books) {
			BookDto bookDto = new BookDto();
			BeanUtils.copyProperties(bookEntity, bookDto);
			returnValue.add(bookDto);
		}
		
		return returnValue;
	}

	@Override
	public BookDto updateBook(String bookId, BookDto bookDto) {
		BookDto returnValue =  new BookDto();
		BookEntity bookEntity = bookRepository.findByBookId(bookId);
		
		if(bookEntity == null) {
			throw new BookServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		if (bookDto.getTitle() == null || bookDto.getTitle().isBlank() ||
			    bookDto.getGenre() == null || bookDto.getGenre().isBlank() ||
			    bookDto.getPublishedYear() <= 0 ||
			    bookDto.getAuthor() == null || bookDto.getAuthor().isBlank()) {
			    
			    throw new BookServiceException(
			        "Invalid input: Title, Genre, and Author are required, and Published Year must be a positive number."
			    );
		}
		
		bookEntity.setTitle(bookDto.getTitle());
		bookEntity.setGenre(bookDto.getGenre());
		bookEntity.setAuthor(bookDto.getAuthor());
		bookEntity.setPublishedYear(bookDto.getPublishedYear());
		
		BookEntity updatedBookDetails = bookRepository.save(bookEntity);
		BeanUtils.copyProperties(updatedBookDetails, returnValue);
		
		return returnValue;
	}

}
