package com.techsorcerer.library_management.service;

import java.util.List;

import com.techsorcerer.library_management.shared.dto.BookBorrowDto;
import com.techsorcerer.library_management.ui.model.response.BookStatus;
import com.techsorcerer.library_management.ui.model.response.BorrowHistoryRest;
import com.techsorcerer.library_management.ui.model.response.UserBorrowHistoryRest;

public interface BookBorrowService {

	BookBorrowDto borrowBook(BookBorrowDto borrowDto);

	BorrowHistoryRest getBorrowedBookDetails(String borrowId);

	BookBorrowDto returnBook( BookBorrowDto borrowDto);

	List<BookBorrowDto> getAllBorrowedBooks();


	void deleteBorrowRecord(String borrowId);

	List<BookBorrowDto> getBookByStatus(String status);

	UserBorrowHistoryRest getUserBorrowHistory(String userId);

	
	

}
