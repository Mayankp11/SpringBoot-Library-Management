package com.techsorcerer.library_management.service;

import java.util.List;

import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.ui.model.response.BorrowHistoryRest;

public interface BookBorrowService {

	BookBorrowDto borrowBook(BookBorrowDto borrowDto);

	BorrowHistoryRest getBorrowedBookDetails(String borrowId);

	BookBorrowDto returnBook( BookBorrowDto borrowDto);

	List<BookBorrowDto> getAllBorrowedBooks();
	
	

}
