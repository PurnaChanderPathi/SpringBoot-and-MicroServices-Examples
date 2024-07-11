package com.example.exception;

public class InvalidFileExtensionException extends RuntimeException {

	public InvalidFileExtensionException(String message) {
		super(message);
	}

}
