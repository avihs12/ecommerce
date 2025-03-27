package com.practice.Exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String message ) {
		super(message);
		System.out.println("Not founded");
	}
	
}
