package com.techsorcerer.library_management.service;

import com.techsorcerer.library_management.shared.dto.BookDto;

public interface BookService {
	BookDto addBook(BookDto book);

	BookDto getBookByBookId(String id);
}
