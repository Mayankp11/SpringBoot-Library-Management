package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.techsorcerer.library_management.ui.model.request.BookDetailsRequestModel;
import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;
import com.techsorcerer.library_management.ui.model.response.BookRest;
import com.techsorcerer.library_management.ui.model.response.BorrowHistoryRest;
import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;
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

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BookBorrowRest borrowBook(@RequestBody BookBorrowRequestDetails borrowRequestDetails) {
		ModelMapper modelMapper = new ModelMapper();

		BookBorrowDto borrowDto = modelMapper.map(borrowRequestDetails, BookBorrowDto.class);
		BookBorrowDto borrowedBook = bookBorrowService.borrowBook(borrowDto);

		BookBorrowRest returnValue = modelMapper.map(borrowedBook, BookBorrowRest.class);

		return returnValue;
	}

	@GetMapping(path = "/{borrowId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BorrowHistoryRest borrowedBookDetailsById(@PathVariable String borrowId) {
		ModelMapper modelMapper = new ModelMapper();
		BorrowHistoryRest borrowedDetails = bookBorrowService.getBorrowedBookDetails(borrowId);

		BorrowHistoryRest returnValue = modelMapper.map(borrowedDetails, BorrowHistoryRest.class);
		
		return returnValue;
	}

	@GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBorrowRest> borrowedBooksDetails() {
		ModelMapper modelMapper = new ModelMapper();
		List<BookBorrowRest> returnValue = new ArrayList<BookBorrowRest>();
		List<BookBorrowDto> borrowDetails = bookBorrowService.getAllBorrowedBooks();
		
		for(BookBorrowDto borrow: borrowDetails) {
			BookBorrowRest borrowHistoryRest = modelMapper.map(borrow, BookBorrowRest.class);
			returnValue.add(borrowHistoryRest);
		}
		
		return returnValue;
		
	}
	
	@PutMapping(value = "/return",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BookBorrowRest returnBorrowedBook(@RequestBody BookBorrowRequestDetails bookBorrowRequestDetails) {
		ModelMapper modelMapper = new ModelMapper();

		BookBorrowDto borrowDto = modelMapper.map(bookBorrowRequestDetails, BookBorrowDto.class);
		System.out.println(borrowDto);
		BookBorrowDto updateBorrowDetails = bookBorrowService.returnBook(borrowDto);

		BookBorrowRest returnValue = modelMapper.map(updateBorrowDetails, BookBorrowRest.class);

		return returnValue;
	}
}
