package com.pkg.Dao;

import java.util.List;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.queryGenerator.QueryExecutor;

public class ContactsDao {

	public static boolean insertContactDetails(int userID, ContactDetails contact) throws Exception {
		if (contact == null || userID < 1) {
			throw new InvalidInputException("Invalid request. Please provide valid contact details.");
		}
		contact.setUserId(userID);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeInsert(contact, true) < 1) {
			throw new Exception("Oops! Something went wrong while saving the contact. Please try again.");
		}
		return true;
	}

	public static List<ContactDetails> getContacts(int userId) throws Exception {
		if (userId < 1) {
			throw new InvalidInputException("Invalid request. Please provide valid contact details.");
		}
		ContactDetails contactDetailsObject = new ContactDetails();
		contactDetailsObject.setUserId(userId);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		List<ContactDetails> resultSet = queryExecutorObject.executeSelect(contactDetailsObject).stream()
				.filter(ContactDetails.class::isInstance).map(ContactDetails.class::cast).toList();
		if (resultSet == null || resultSet.isEmpty()) {
			throw new Exception("Oops! Something went wrong while fetching contacts. Please try again.");
		}
		return resultSet;
	}

	public static boolean deleteContact(int contactId) throws Exception {
		if (contactId < 1) {
			throw new InvalidInputException("Invalid request. Please provide valid contact details.");
		}
		QueryExecutor queryExecutorObject = new QueryExecutor();
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setContactId(contactId);
		if (queryExecutorObject.executeDelete(categoryMapObj) < 0) {
			throw new Exception("Oops! Something went wrong. Please try again");
		}

		ContactDetails contactDetailsObj = new ContactDetails();
		contactDetailsObj.setContactId(contactId);
		if (queryExecutorObject.executeDelete(contactDetailsObj) < 1) {
			throw new Exception("Oops! Something went wrong. Please try again.");
		}
		return true;
	}

	public static boolean updateContact(ContactDetails contact) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		if(contact == null) {
			throw new InvalidInputException("Contact is empty");
		}
		QueryExecutor queryExecutorObject = new QueryExecutor();
		int result = queryExecutorObject.executeUpdate(contact);
		return result > 0;
	}

}