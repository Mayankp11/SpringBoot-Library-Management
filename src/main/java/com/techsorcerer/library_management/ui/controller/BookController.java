package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.exceptions.MissingRequestBodyException;
import com.techsorcerer.library_management.service.BookService;
import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.shared.dto.BookDto;
import com.techsorcerer.library_management.ui.model.request.BookDetailsRequestModel;
import com.techsorcerer.library_management.ui.model.response.BookRest;
import com.techsorcerer.library_management.ui.model.response.ErrorMessages;
import com.techsorcerer.library_management.ui.model.response.OperationStatusModel;
import com.techsorcerer.library_management.ui.model.response.RequestOperationName;
import com.techsorcerer.library_management.ui.model.response.RequestOperationStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("library")
public class BookController {

<<<<<<< HEAD
	//commit test
	
=======
>>>>>>> commits-recovery-branch
	@Autowired
	BookService bookService;
	
	

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BookRest addBooks(@Valid @RequestBody BookDetailsRequestModel bookDetails, BindingResult bindingResult) {
		
//		if @ReuestBody (required = false)
		//if(bookDetails == null) {
//			System.out.println("Body is missing, throwing MissingRequestBodyException");
//			throw new MissingRequestBodyException(ErrorMessages.MISSING_REQUEST_BODY);
//		}
		if(bindingResult.hasErrors()) {
		String requiredFieldsErrorMessages = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
			throw new MissingRequestBodyException(ErrorMessages.MISSING_REQUEST_BODY, requiredFieldsErrorMessages);
		}
		
		BookRest returnValue = new BookRest();

		BookDto bookDto = new BookDto();
		BeanUtils.copyProperties(bookDetails, bookDto);

		BookDto addedABook = bookService.addBook(bookDto);
		BeanUtils.copyProperties(addedABook, returnValue);

		return returnValue;
	}

	// Get book by a book id
	@GetMapping(path = "/{id}" , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BookRest getBookById(@PathVariable String id) {
		BookRest returnValue = new BookRest();
		BookDto bookDto = bookService.getBookByBookId(id);

		BeanUtils.copyProperties(bookDto, returnValue);

		return returnValue;
	}

	@GetMapping
	public List<BookRest> getBooks(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "20")int limit){
		
		List<BookRest> returnValue = new ArrayList<>();
		List<BookDto> books = bookService.getBooks(page,limit);
		
		for(BookDto bookDto: books) {
			BookRest userModel = new BookRest();
			BeanUtils.copyProperties(bookDto, userModel);
			returnValue.add(userModel);
		}
		
		return returnValue;		
	}

	@PutMapping(path = "/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public BookRest updateBooks(@PathVariable String id, @RequestBody BookDetailsRequestModel bookDetailsRequestModel) {
		BookRest returnValue = new BookRest();
		ModelMapper modelMapper = new ModelMapper();
		BookDto bookDto = modelMapper.map(bookDetailsRequestModel, BookDto.class);
		
		BookDto updateBook = bookService.updateBook(id, bookDto);
		BeanUtils.copyProperties(updateBook, returnValue);
		
		return returnValue;
	}

	@DeleteMapping(path = "/{id}",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel deleteBook(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		bookService.deleteBook(id);
		
		returnValue.setOperationResult(RequestOperationStatus.Success.name());
		return returnValue;
	}
}
