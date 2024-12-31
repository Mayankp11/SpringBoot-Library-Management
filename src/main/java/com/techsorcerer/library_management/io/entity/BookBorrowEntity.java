package com.techsorcerer.library_management.io.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrow_records")
public class BookBorrowEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String borrowId;
	
	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "bookId", nullable = false)
	private BookEntity bookId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
	private LibraryUserEntity userId;
	
	@Column(nullable = false)
	private LocalDateTime borrowDate;
	
	@Column(nullable = false)
	private LocalDateTime returnDate;
	
	@Column(nullable = false, length = 20)
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public BookEntity getBookId() {
		return bookId;
	}

	public void setBookId(BookEntity bookId) {
		this.bookId = bookId;
	}

	public LibraryUserEntity getUserId() {
		return userId;
	}

	public void setUserId(LibraryUserEntity userId) {
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
}