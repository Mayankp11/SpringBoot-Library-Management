package com.techsorcerer.library_management.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsorcerer.library_management.exceptions.BookBorrowException;
import com.techsorcerer.library_management.exceptions.BookServiceException;
import com.techsorcerer.library_management.exceptions.LibraryUserServiceException;
import com.techsorcerer.library_management.io.entity.BookBorrowEntity;
import com.techsorcerer.library_management.io.entity.BookEntity;
import com.techsorcerer.library_management.io.entity.LibraryUserEntity;
import com.techsorcerer.library_management.io.repository.BookBorrowRepository;
import com.techsorcerer.library_management.io.repository.BookRepository;
import com.techsorcerer.library_management.io.repository.LibraryUserRepository;
import com.techsorcerer.library_management.service.BookBorrowService;
import com.techsorcerer.library_management.shared.Utils;
import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.ui.model.response.BookStatus;
import com.techsorcerer.library_management.ui.model.response.ErrorMessages;

@Service
public class BookBorrowServiceImpl implements BookBorrowService {

	@Autowired
	BookBorrowRepository bookBorrowRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	LibraryUserRepository libraryUserRepository;
	
	@Autowired
	Utils utis;

	@Override
	public BookBorrowDto borrowBook(BookBorrowDto borrowDto) {
		ModelMapper modelMapper = new ModelMapper();
		BookBorrowEntity borrowEntity = new BookBorrowEntity();
		
		BookEntity bookEntity = bookRepository.findByBookId(borrowDto.getBookId());
		if(bookEntity == null) {
			throw new BookServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}else {			
			borrowEntity.setBookId(bookEntity);
		}
		
		LibraryUserEntity libraryUserEntity = libraryUserRepository.findByUserId(borrowDto.getUserId());
		if(libraryUserEntity == null) {
			throw new LibraryUserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}else {			
			borrowEntity.setUserId(libraryUserEntity);
		}
		
		BookBorrowEntity checkStatus = bookBorrowRepository.findByBookIdAndStatus(bookEntity,BookStatus.BORROWED.toString());
		if(checkStatus != null ) {
			throw new BookBorrowException(ErrorMessages.BOOK_IS_ALREADY_ISSUED.getErrorMessage());
		}

		if(borrowEntity.getBorrowId() == null) {
			borrowEntity.setBorrowId(utis.generateBorrowId(20));
		}
		if (borrowEntity.getBorrowDate() == null) {
			borrowEntity.setBorrowDate(LocalDateTime.now());
		}
		if (borrowEntity.getReturnDate() == null) {
			borrowEntity.setReturnDate(LocalDateTime.now().plusDays(30));
		}
		borrowEntity.setStatus(BookStatus.BORROWED.name());

		BookBorrowEntity storeDetails = bookBorrowRepository.save(borrowEntity);
		BookBorrowDto returnValue = modelMapper.map(storeDetails, BookBorrowDto.class);
		
		return returnValue;
	}

	@Override
	public BookBorrowDto getBorrowedBookDetails(String borrowId) {
		ModelMapper modelMapper = new ModelMapper();
		BookBorrowEntity borrowEntity = bookBorrowRepository.findByBorrowId(borrowId);
		if(borrowEntity == null) {
			throw new BookBorrowException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} 
		
		BookBorrowDto returnvalue = modelMapper.map(borrowEntity, BookBorrowDto.class);
		return returnvalue;
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public BookBorrowDto returnBook(BookBorrowDto borrowDto) {
		
		BookBorrowEntity borrowEntity = bookBorrowRepository.findByBorrowId(borrowDto.getBorrowId());
		
		if(borrowEntity == null) {
			throw new BookBorrowException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		if (!borrowEntity.getUserId().equals(borrowDto.getUserId())) {
	        throw new BookBorrowException("User ID does not match the borrow record.");
	    }
		
		if(borrowEntity.getStatus().equals(BookStatus.RETURNED.name())) {
			throw new BookBorrowException("Book is already returned");
		}
			
			borrowEntity.setStatus(BookStatus.RETURNED.name());
			borrowEntity.setReturnDate(LocalDateTime.now());
		
		
		bookBorrowRepository.save(borrowEntity);
		ModelMapper modelMapper = new ModelMapper();
		BookBorrowDto updatedBorrowRecord = modelMapper.map(borrowEntity, BookBorrowDto.class);
		return updatedBorrowRecord;
	}

}
