package com.techsorcerer.library_management.ui.model.response;

import java.util.List;

public class BookHistoryRest {
	
	private BookRest bookRest;
	private List<BookUserHistoryRest> listOfUsers;
	
	public List<BookUserHistoryRest> getListOfUsers() {
		return listOfUsers;
	}
	public void setListOfUsers(List<BookUserHistoryRest> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}
	public BookRest getBookRest() {
		return bookRest;
	}
	public void setBookRest(BookRest bookRest) {
		this.bookRest = bookRest;
	}
	
	
	

}
