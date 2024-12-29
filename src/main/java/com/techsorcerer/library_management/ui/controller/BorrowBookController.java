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

import com.techsorcerer.library_management.service.BookBorrowService;
import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.ui.model.request.BookBorrowRequestDetails;

import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;

@RestController
@RequestMapping("borrow")
public class BorrowBookController {
	@Autowired
	BookBorrowService bookBorrowService;

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

		return returnValue;
	}

	@PutMapping("/return")
	public BookBorrowRest returnBorrowedBook(@RequestBody BookBorrowRequestDetails bookBorrowRequestDetails) {
		ModelMapper modelMapper = new ModelMapper();
	    modelMapper.typeMap(BookBorrowRequestDetails.class, BookBorrowDto.class)
        .addMappings(mapper -> {
            mapper.skip(BookBorrowDto::setBookId);  // Skip bookId
            mapper.skip(BookBorrowDto::setBorrowDate);  // Skip borrowDate
            mapper.skip(BookBorrowDto::setStatus);  // Skip status (optional)
        });
		BookBorrowDto borrowDto = modelMapper.map(bookBorrowRequestDetails, BookBorrowDto.class);
		System.out.println(borrowDto);
		BookBorrowDto updateBorrowDetails = bookBorrowService.returnBook(borrowDto);

		BookBorrowRest returnValue = modelMapper.map(updateBorrowDetails, BookBorrowRest.class);

		return returnValue;
	}
}
