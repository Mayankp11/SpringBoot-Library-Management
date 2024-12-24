package com.techsorcerer.library_management.ui.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDetailsRequestModel {
	private String bookId;

	@NotBlank(message = "Title is required")
	private String title;
	@NotBlank(message = "Genre is required")
	private String genre;
	@NotBlank(message = "Author is required")
	private String author;
	@NotNull(message = "Published Year is required")
	private Integer publishedYear;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}

}
