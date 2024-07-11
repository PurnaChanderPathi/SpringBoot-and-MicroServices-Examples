package com.example.exception;

public class FolderDoesNotExistsException extends RuntimeException {
	public FolderDoesNotExistsException(String message) {
		super(message);
	}
}
