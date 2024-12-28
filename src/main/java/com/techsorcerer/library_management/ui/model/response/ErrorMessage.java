package com.techsorcerer.library_management.ui.model.response;

import java.time.LocalDateTime;

public class ErrorMessage {
	private LocalDateTime timeStamp;
	private String message;
	private String errorCode;

	public ErrorMessage() {
	}

	public ErrorMessage(LocalDateTime timeStamp, String message, String errorCode) {

		this.timeStamp = timeStamp;
		this.message = message;
		this.errorCode = errorCode;

	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;

	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
