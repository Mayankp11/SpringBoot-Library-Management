package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.DeleteMapping;

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
import com.techsorcerer.library_management.ui.model.request.BookReturnRequestModel;
import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;
import com.techsorcerer.library_management.ui.model.response.BookRest;
import com.techsorcerer.library_management.ui.model.response.BookStatus;
import com.techsorcerer.library_management.ui.model.response.BorrowHistoryRest;
import com.techsorcerer.library_management.ui.model.response.BookBorrowRest;
import com.techsorcerer.library_management.ui.model.response.LibraryUserRest;

import com.techsorcerer.library_management.ui.model.response.OperationStatusModel;
import com.techsorcerer.library_management.ui.model.response.RequestOperationName;
import com.techsorcerer.library_management.ui.model.response.RequestOperationStatus;


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
		
		BorrowHistoryRest borrowedDetails = bookBorrowService.getBorrowedBookDetails(borrowId);
		 return borrowedDetails;
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
	
	@GetMapping(value = "/history/borrowed", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBorrowRest> getBorrowedBooks() {
		ModelMapper modelMapper = new ModelMapper();
		List<BookBorrowRest> borrowedBooks = new ArrayList<>();
		List<BookBorrowDto> borrowDetails = bookBorrowService.getBookByStatus(BookStatus.BORROWED.name());
		
		for(BookBorrowDto dto: borrowDetails) {
			BookBorrowRest borrowRest = modelMapper.map(dto, BookBorrowRest.class);
			borrowedBooks.add(borrowRest);
		}
		
		return borrowedBooks;
		
	}
	@GetMapping(value = "/history/returned", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BookBorrowRest> getReturnedBooks() {
		ModelMapper modelMapper = new ModelMapper();
		List<BookBorrowRest> borrowedBooks = new ArrayList<>();
		List<BookBorrowDto> borrowDetails = bookBorrowService.getBookByStatus(BookStatus.RETURNED.name());
		
		for(BookBorrowDto dto: borrowDetails) {
			BookBorrowRest borrowRest = modelMapper.map(dto, BookBorrowRest.class);
			borrowedBooks.add(borrowRest);
		}
		
		return borrowedBooks;
		
	}
	
	@PutMapping(value = "/return", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public BookBorrowRest returnBorrowedBook(@RequestBody BookReturnRequestModel bookReturnRequestModel) {
    // Initialize ModelMapper for mapping DTOs
    ModelMapper modelMapper = new ModelMapper();

    // Adding custom property mappings (if needed) using PropertyMap
    modelMapper.addMappings(new PropertyMap<BookReturnRequestModel, BookBorrowDto>() {
        protected void configure() {
            // Specify how to map the borrowId and userId from the request model to the DTO
            map(source.getBorrowId(), destination.getBorrowId());
            map(source.getUserId(), destination.getUserId());
        }
    });

    // Map the incoming request data to the BookBorrowDto
    BookBorrowDto borrowDto = modelMapper.map(bookReturnRequestModel, BookBorrowDto.class);

    // Call the service to return the book and get the updated borrow record
    BookBorrowDto updatedBorrowRecord = bookBorrowService.returnBook(borrowDto);

    // Map the updated borrow DTO back to the REST response object (BookBorrowRest)
    BookBorrowRest returnValue = modelMapper.map(updatedBorrowRecord, BookBorrowRest.class);

    // Return the response to the client
    return returnValue;
}

	@DeleteMapping(path = "/{borrowId}")
	public OperationStatusModel deleteABorrowRecord(@PathVariable String borrowId) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		bookBorrowService.deleteBorrowRecord(borrowId);
		
		returnValue.setOperationResult(RequestOperationStatus.Success.name());
		return returnValue;
		
	}



}
