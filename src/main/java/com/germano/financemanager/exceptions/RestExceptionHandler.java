package com.germano.financemanager.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exc) {
		List<FormErrorDto> errorsDto = new ArrayList<>();
		List<FieldError> fieldErrors = exc.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(error -> {
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			FormErrorDto errorDto = new FormErrorDto(error.getField(), message);
			errorsDto.add(errorDto);
		});
		
		return errorsDto;
	}
}
