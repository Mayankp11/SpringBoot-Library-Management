package com.techsorcerer.library_management.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.io.entity.BookEntity;
import com.techsorcerer.library_management.io.entity.LibraryUserEntity;
import com.techsorcerer.library_management.io.repository.BookRepository;
import com.techsorcerer.library_management.io.repository.LibraryUserRepository;
import com.techsorcerer.library_management.service.BookBorrowService;
import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.ui.model.request.BookBorrowRequestDetails;

import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;
import com.techsorcerer.library_management.ui.model.response.BookRest;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;

@RestController
@RequestMapping("borrow")
public class BorrowBookController {
	@Autowired
	BookBorrowService bookBorrowService;
	
	@Autowired
	LibraryUserRepository libraryUserRepository;
	
	@Autowired
	BookRepository bookRepository;

	@PostMapping
	public BookBorrowRest borrowBook(@RequestBody BookBorrowRequestDetails borrowRequestDetails) {
		ModelMapper modelMapper = new ModelMapper();

		BookBorrowDto borrowDto = modelMapper.map(borrowRequestDetails, BookBorrowDto.class);
		BookBorrowDto borrowedBook = bookBorrowService.borrowBook(borrowDto);

		BookBorrowRest returnValue = modelMapper.map(borrowedBook, BookBorrowRest.class);

		return returnValue;
	}

	@GetMapping(path = "/{borrowId}")
	public BookBorrowRest borrowedBookDetailsById(@PathVariable String borrowId) {
		ModelMapper modelMapper = new ModelMapper();
		BookBorrowDto borrowedDetails = bookBorrowService.getBorrowedBookDetails(borrowId);

		BookBorrowRest returnValue = modelMapper.map(borrowedDetails, BookBorrowRest.class);
		
		 // Fetching the related user and book details from the respective repositories
	    LibraryUserEntity libraryUserEntity = libraryUserRepository.findByUserId(borrowedDetails.getUserId());
	    BookEntity bookEntity = bookRepository.findByBookId(borrowedDetails.getBookId());
	    
	    
	    
	    // Mapping the user and book entities to their respective response objects (DTOs)
	    LibraryUserRest userRest = modelMapper.map(libraryUserEntity, LibraryUserRest.class);
	    BookRest bookRest = modelMapper.map(bookEntity, BookRest.class);
	    
	    // Setting the user and book details into the response object
	    returnValue.setLibraryUserRest(userRest);
	    returnValue.setBookRest(bookRest);

		return returnValue;
	}

	@PutMapping("/return")
	public BookBorrowRest returnBorrowedBook(@RequestBody BookBorrowRequestDetails bookBorrowRequestDetails) {
		ModelMapper modelMapper = new ModelMapper();

		BookBorrowDto borrowDto = modelMapper.map(bookBorrowRequestDetails, BookBorrowDto.class);
		System.out.println(borrowDto);
		BookBorrowDto updateBorrowDetails = bookBorrowService.returnBook(borrowDto);

		BookBorrowRest returnValue = modelMapper.map(updateBorrowDetails, BookBorrowRest.class);

		return returnValue;
	}
}
