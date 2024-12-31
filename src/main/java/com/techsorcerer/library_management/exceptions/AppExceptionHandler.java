package com.techsorcerer.library_management.exceptions;


import java.time.LocalDateTime;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.techsorcerer.library_management.ui.model.response.ErrorMessage;



@RestControllerAdvice
public class AppExceptionHandler {
	/// webRequest gives access to http error requests, cookies and more
		@ExceptionHandler(value = { BookServiceException.class })
		public ResponseEntity<Object> handleUserServiceException(BookServiceException ex, WebRequest webRequest) 
		{
			String errorCode = "Book Service Error";
			ErrorMessage errorMessage = new ErrorMessage( LocalDateTime.now(), ex.getMessage(), errorCode);
			
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(value = {LibraryUserServiceException.class})
		public ResponseEntity<Object> handleLibraryUserServiceException(LibraryUserServiceException ex, WebRequest webRequest){
			String errorCode = "Library User Service Error";
			ErrorMessage errorMessage = new ErrorMessage( LocalDateTime.now(), ex.getMessage(), errorCode);
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		@ExceptionHandler(value = {BookBorrowException.class})
		public ResponseEntity<Object> handleBookBorrowException(BookBorrowException ex, WebRequest webRequest){
			String errorCode = "Book Borrow Error";
			ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), errorCode);
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(value = { Exception.class })
		public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest webRequest) 
		{
			String errorCode = "Internal Server Error";
			ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), errorCode);
			
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

}
