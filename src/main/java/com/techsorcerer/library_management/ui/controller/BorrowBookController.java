package com.techsorcerer.library_management.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping
	public String returnBook() {
		return "return book";
	}
}
