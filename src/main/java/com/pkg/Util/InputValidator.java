package com.pkg.Util;

import java.util.regex.Pattern;

import com.pkg.Exceptions.InvalidInputException;

public class InputValidator {
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[0-9]{3,15}$");
	private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
	private static final Pattern SAFE_STRING_PATTERN = Pattern.compile("^[a-zA-Z0-9_. ]+$");	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
	public static void validateSafeString(String value, String fieldName) throws InvalidInputException {
		if(value.isEmpty()) {
			return;
		}
		if(!SAFE_STRING_PATTERN.matcher(value).matches()) {
			throw new InvalidInputException(fieldName+" contains invalid characters.");
		}
	}
	public static void validatePhoneNumber(String phoneNumber) throws InvalidInputException{
		if(phoneNumber.isEmpty()) {
			return;
		}
		if(!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
			throw new InvalidInputException("Phone number should be exactly 10 digits");
		}
	}
	public static void validateDate(String date) throws InvalidInputException{
		if(date.isEmpty()) {
			return;
		}
		if(!DATE_PATTERN.matcher(date).matches()) {
			throw new InvalidInputException("Follow the format yyyy-mm-dd.");
		}
	}
	public static void validateEmail(String email) throws InvalidInputException {
		if(email.isEmpty()) {
			return;
		}
		if(!EMAIL_PATTERN.matcher(email).matches()) {
			throw new InvalidInputException("Enter a valid Email ID");
		}
	}
	public static void main(String[] args) throws InvalidInputException {
		InputValidator.validateEmail("a@gmail.com");
	}
}
