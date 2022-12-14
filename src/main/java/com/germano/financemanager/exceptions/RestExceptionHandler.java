package com.germano.financemanager.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(
			DataIntegrityViolationException exc) {
		
		ErrorResponse error = new ErrorResponse(
				exc.getLocalizedMessage(), 
				HttpStatus.CONFLICT
		);
		
		return new ResponseEntity<>(error, error.getStatus());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(
			EntityNotFoundException exc) {
		
		ErrorResponse error = new ErrorResponse(
				exc.getLocalizedMessage(), 
				HttpStatus.NOT_FOUND
		);
		
		return new ResponseEntity<>(error, error.getStatus());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException exc) {
		
		ErrorResponse error = new ErrorResponse(
				exc.getLocalizedMessage(), 
				HttpStatus.BAD_REQUEST
		);
		
		return new ResponseEntity<>(error, error.getStatus());
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<FormErrorDto>> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exc) {
		List<FormErrorDto> errorsDto = new ArrayList<>();
		List<FieldError> fieldErrors = exc.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(error -> {
			FormErrorDto errorDto = new FormErrorDto(
					error.getField(), 
					error.getDefaultMessage()
					);
			errorsDto.add(errorDto);
		});
		
		return new ResponseEntity<>(errorsDto, HttpStatus.BAD_REQUEST);
	}
	
}
