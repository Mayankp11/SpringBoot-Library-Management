package com.techsorcerer.library_management.service;

import com.techsorcerer.library_management.shared.dto.BookBorrowDto;

public interface BookBorrowService {

	BookBorrowDto borrowBook(BookBorrowDto borrowDto);

}
