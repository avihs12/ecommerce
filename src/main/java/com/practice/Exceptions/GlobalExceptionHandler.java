package com.practice.Exceptions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException re){	
		Map <String,Object> response = new HashMap<>();
		response.put("timestamp", LocalDate.now());
		response.put("message", re.getMessage());
		response.put("status",HttpStatus.NOT_FOUND.value());
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodNotFoundException(MethodArgumentNotValidException me){
			Map<String,String> res = new HashMap<>();
			me.getAllErrors().forEach((errors)->{
	            res.put("timestamp", LocalDate.now().toString());
	            res.put("status", HttpStatus.BAD_REQUEST.toString());
				String fieldName = ((FieldError) errors).getField();
	            String errorMessage = errors.getDefaultMessage();
	            res.put(fieldName, errorMessage);
	           System.out.println("shivaaa:::: "+me.getAllErrors());
			});
			return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Map<String,Object>> handleNulls(NullPointerException ne){
		Map<String,Object> res = new HashMap<>();
		res.put("errorStatus","Internal server error");
		res.put("timeStamp", LocalDate.now());
		res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		res.put("error", ne.getMessage());
		System.out.println("this is reponse in console "+res);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleData(DataIntegrityViolationException de){
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate record ");
	}
	
	
}
