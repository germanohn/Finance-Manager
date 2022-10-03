package com.germano.financemanager.exceptions;

public class DespesaNotFoundException extends RuntimeException {

	public DespesaNotFoundException() {
	}
	
	public DespesaNotFoundException(String message) {
		super(message);
	}
	
	public DespesaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
