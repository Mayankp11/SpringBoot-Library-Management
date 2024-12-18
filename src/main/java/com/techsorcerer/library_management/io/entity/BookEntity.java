package com.techsorcerer.library_management.io.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "books")
public class BookEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, length = 10)
	private long bookId;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(nullable = false, length = 15)
	private String genre;

	@Column(nullable = false, length = 15)
	private String author;

	@Column(nullable = false, length = 10)
	private String publishedYear;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
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

	public String getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(String publishedYear) {
		this.publishedYear = publishedYear;
	}

}
