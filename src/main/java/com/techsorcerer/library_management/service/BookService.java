package com.techsorcerer.library_management.service;

import java.util.List;

import com.techsorcerer.library_management.shared.dto.BookDto;
import com.techsorcerer.library_management.ui.model.response.BookRest;

public interface BookService {
	BookDto addBook(BookDto book);

	BookDto getBookByBookId(String id);

	List<BookDto> getBooks(int page, int limit);
}
