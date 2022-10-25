package com.germano.financemanager.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> handleMethodArgumentNotValid(
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
		
		return errorsDto;
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(
			DataIntegrityViolationException exc) {
		
		ErrorResponse error = new ErrorResponse(
				exc.getLocalizedMessage(), 
				HttpStatus.CONFLICT
		);
		
		return new ResponseEntity<>(error.getMessage(), error.getStatus());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(
			EntityNotFoundException exc) {
		
		ErrorResponse error = new ErrorResponse(
				exc.getLocalizedMessage(), 
				HttpStatus.NOT_FOUND
		);
		
		return new ResponseEntity<>(error.getMessage(), error.getStatus());
	}
}
