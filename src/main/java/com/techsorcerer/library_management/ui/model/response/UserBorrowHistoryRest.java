package com.techsorcerer.library_management.ui.model.response;

import java.util.List;

public class UserBorrowHistoryRest {
	
	private LibraryUserRest libraryUser;
	private List<BookBorrowRest> borrowedBooks;
	
	public LibraryUserRest getLibraryUser() {
		return libraryUser;
	}
	public void setLibraryUser(LibraryUserRest libraryUser) {
		this.libraryUser = libraryUser;
	}
	public List<BookBorrowRest> getBorrowedBooks() {
		return borrowedBooks;
	}
	public void setBorrowedBooks(List<BookBorrowRest> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

}
