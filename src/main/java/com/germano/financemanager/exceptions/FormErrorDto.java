package com.germano.financemanager.exceptions;

public class FormErrorDto {

	private String field;
	private String message;
	
	public FormErrorDto() {
	}

	public FormErrorDto(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
}
