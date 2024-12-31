package com.techsorcerer.library_management.exceptions;

import com.techsorcerer.library_management.ui.model.response.ErrorMessages;

public class MissingRequestBodyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingRequestBodyException(ErrorMessages errorMessages, String customMessage) {
        super(errorMessages.getErrorMessage() +": "+ customMessage);  // Ensure the message is passed correctly
    }
}

//public class MissingRequestBodyException extends RuntimeException {
//
//    private static final long serialVersionUID = 1L;
//
//    public MissingRequestBodyException(ErrorMessages errorMessages) {
//        super(errorMessages.getErrorMessage());  // This should correctly pass the error message string
//    }
//}
