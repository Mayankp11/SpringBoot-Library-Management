package com.techsorcerer.library_management.ui.model.response;

import java.time.LocalDateTime;

public class BorrowHistoryRest {
	private String borrowId;
	private String bookId;
	private String userId;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;
	private String status;
	private LibraryUserRest libraryUserRest;
	private BookRest bookRest;
	
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDateTime borrowDate) {
		this.borrowDate = borrowDate;
	}
	public LocalDateTime getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LibraryUserRest getLibraryUserRest() {
		return libraryUserRest;
	}
	public void setLibraryUserRest(LibraryUserRest libraryUserRest) {
		this.libraryUserRest = libraryUserRest;
	}
	public BookRest getBookRest() {
		return bookRest;
	}
	public void setBookRest(BookRest bookRest) {
		this.bookRest = bookRest;
	}

}
