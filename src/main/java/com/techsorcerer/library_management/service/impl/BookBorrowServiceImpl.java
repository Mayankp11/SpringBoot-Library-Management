package com.techsorcerer.library_management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.techsorcerer.library_management.ui.model.response.BookRest;
import com.techsorcerer.library_management.ui.model.response.BookStatus;
import com.techsorcerer.library_management.ui.model.response.BorrowHistoryRest;
import com.techsorcerer.library_management.ui.model.response.ErrorMessages;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;

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
		if (bookEntity == null) {
			throw new BookServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} else {
			borrowEntity.setBookId(bookEntity);
		}

		LibraryUserEntity libraryUserEntity = libraryUserRepository.findByUserId(borrowDto.getUserId());
		if (libraryUserEntity == null) {
			throw new LibraryUserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		} else {
			borrowEntity.setUserId(libraryUserEntity);
		}

		BookBorrowEntity checkStatus = bookBorrowRepository.findByBookIdAndStatus(bookEntity,
				BookStatus.BORROWED.toString());
		if (checkStatus != null) {
			throw new BookBorrowException(ErrorMessages.BOOK_IS_ALREADY_ISSUED.getErrorMessage());
		}

		if (borrowEntity.getBorrowId() == null) {
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
	public BorrowHistoryRest getBorrowedBookDetails(String borrowId) {
		ModelMapper modelMapper = new ModelMapper();
		BookBorrowEntity borrowEntity = bookBorrowRepository.findByBorrowId(borrowId);
		if (borrowEntity == null) {
			throw new BookBorrowException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		}
		
		BorrowHistoryRest returnvalue = modelMapper.map(borrowEntity, BorrowHistoryRest.class);
		  // Use the associated entities from the borrow entity
	    LibraryUserEntity userEntity = borrowEntity.getUserId(); // Assuming getUserId() returns LibraryUserEntity
	    BookEntity bookEntity = borrowEntity.getBookId();        // Assuming getBookId() returns BookEntity

	    if (userEntity == null) {
	        throw new BookBorrowException("User details not found for the given borrow record.");
	    }
	    if (bookEntity == null) {
	        throw new BookBorrowException("Book details not found for the given borrow record.");
	    }

		
		   // Map the user and book entities to their respective response objects
	    LibraryUserRest userDetails = modelMapper.map(userEntity, LibraryUserRest.class);
	    BookRest bookDetails = modelMapper.map(bookEntity, BookRest.class);

	    // Set the user and book details in the response
	    returnvalue.setBookRest(bookDetails);
	    returnvalue.setLibraryUserRest(userDetails);

	    
		return returnvalue;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public BookBorrowDto returnBook(BookBorrowDto borrowDto) {
	    // Fetch the borrow entity from the database using borrowId
	    BookBorrowEntity borrowEntity = bookBorrowRepository.findByBorrowId(borrowDto.getBorrowId());
	  

	    if (borrowEntity == null) {
	        throw new BookBorrowException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	    }
	    System.out.println("Hello");
	    
	    System.out.println(borrowEntity.getBookId().getBookId());
	    System.out.println(borrowDto.getBookId());
	    if( !borrowEntity.getBookId().getBookId().equals(borrowDto.getBookId()) || !borrowEntity.getUserId().getUserId().equals(borrowDto.getUserId())) {
	    	throw new BookBorrowException("User ID does not match the borrow record.");
	    }

//	    // Ensure that the provided userId matches the userId in the borrow record
//	    if (borrowEntity.getUserId() == null || borrowDto.getUserId() == null || 
//	    	    !borrowEntity.getUserId().equals(borrowDto.getUserId())) {
//	    	    throw new BookBorrowException("User ID does not match the borrow record.");
//	    	}

	    // Ensure that the book is not already returned
	    if (borrowEntity.getStatus().equals(BookStatus.RETURNED.name())) {
	        throw new BookBorrowException("Book is already returned");
	    }

	    // Update the status to RETURNED and set the returnDate to the current time
	    borrowEntity.setStatus(BookStatus.RETURNED.name());
	    borrowEntity.setReturnDate(LocalDateTime.now());

	    // Save the updated borrow entity back to the database
	    bookBorrowRepository.save(borrowEntity);

	    // Map the updated borrow entity to the BookBorrowDto for the response
	    ModelMapper modelMapper = new ModelMapper();
	    BookBorrowDto updatedBorrowRecord = modelMapper.map(borrowEntity, BookBorrowDto.class);
	    return updatedBorrowRecord;
	}


	@Override
	public List<BookBorrowDto> getAllBorrowedBooks() {

		ModelMapper modelMapper = new ModelMapper();
		List<BookBorrowEntity> borrowedBookDetails = (List<BookBorrowEntity>) bookBorrowRepository.findAll();

		List<BookBorrowDto> returnValue = new ArrayList<BookBorrowDto>();

		for (BookBorrowEntity entity : borrowedBookDetails) {
			BookBorrowDto borrowDto = modelMapper.map(entity, BookBorrowDto.class);
			returnValue.add(borrowDto);
		}
		return returnValue;

	}
}
