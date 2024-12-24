package com.techsorcerer.library_management.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techsorcerer.library_management.service.BookService;
import com.techsorcerer.library_management.shared.dto.BookDto;
import com.techsorcerer.library_management.ui.model.request.BookDetailsRequestModel;
import com.techsorcerer.library_management.ui.model.response.BookRest;

@RestController
@RequestMapping("library")
public class LibraryController {

	@Autowired
	BookService bookService;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public BookRest addBooks(@RequestBody BookDetailsRequestModel bookDetails) {
		BookRest returnValue = new BookRest();

		BookDto bookDto = new BookDto();
		BeanUtils.copyProperties(bookDetails, bookDto);

		BookDto addedABook = bookService.addBook(bookDto);
		BeanUtils.copyProperties(addedABook, returnValue);

		return returnValue;
	}

	// Get book by a book id
	@GetMapping(path = "/{id}")
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

	@PutMapping
	public String updateBooks() {
		return "Books are updated";
	}

	@DeleteMapping
	public String deleteBooks() {
		return "Book deleted";
	}
}
