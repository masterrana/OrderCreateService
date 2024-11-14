package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

	private String message;
	private List<ErrorField> errors;
	
	  public ErrorResponse(String message) {
	        this.message = message;
	        this.errors = new ArrayList<>();
	    }

	    public void addError(String field, String message) {
	        this.errors.add(new ErrorField(field, message));
	    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ErrorField> getErrors() {
		return errors;
	}

	public void setErrors(List<ErrorField> errors) {
		this.errors = errors;
	}

	public static class ErrorField {
		private String field;
		private String message;

		public ErrorField(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
