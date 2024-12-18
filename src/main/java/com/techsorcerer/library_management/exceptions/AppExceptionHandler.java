package com.techsorcerer.library_management.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.techsorcerer.library_management.ui.model.response.ErrorMessage;




public class AppExceptionHandler {
	/// webRequest gives access to http error requests, cookies and more
		@ExceptionHandler(value = { BookServiceException.class })
		public ResponseEntity<Object> handleUserServiceException(BookServiceException ex, WebRequest webRequest) 
		{
			ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
			
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		@ExceptionHandler(value = { Exception.class })
		public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest webRequest) 
		{
			ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
			
			
			return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

}
