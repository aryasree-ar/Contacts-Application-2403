package com.pkg.Dao;


import java.util.ArrayList;
import java.util.List;

import com.pkg.POJO.DbPojo;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.queryGenerator.QueryGenerator;

public class ContactsDao {
	
	static String query;
	
	//inserting contact's details
	public static boolean insertContactDetails(int userID,ContactDetails contact) {
		boolean reslut = false;
		try {
			contact.setUserId(userID);
			QueryGenerator queryGenerator = new QueryGenerator();
			reslut = queryGenerator.insert(contact) > 0 ;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return reslut; 
	}
	
	//to display contacts
	public static List<ContactDetails> getContacts(int userId) {
		
		ContactDetails contactDetailsObject = new ContactDetails();
		contactDetailsObject.setUserId(userId);
		
		QueryGenerator queryGenerator = new QueryGenerator();
		List<DbPojo> resultSet = queryGenerator.select(contactDetailsObject);
		
		List<ContactDetails> contactDetails = new ArrayList<>();
		for(DbPojo dbPojo: resultSet) {
			if(dbPojo instanceof ContactDetails) {
				contactDetails.add((com.pkg.POJO.ContactDetails) dbPojo);
			}
		}
		return contactDetails;

	}
	//to delete contacts
	public static boolean deleteContact(int contactId) throws IllegalArgumentException, IllegalAccessException {
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setContactID(contactId);
		if(categoryMapObj != null) {
			QueryGenerator queryGenerator = new QueryGenerator();
			queryGenerator.delete(categoryMapObj);
		}

		ContactDetails contactDetailsObj = new ContactDetails();
		contactDetailsObj.setContactId(contactId);
		QueryGenerator queryGenerator = new QueryGenerator();
		queryGenerator.delete(contactDetailsObj);
		return true;
	}
	
	//to update contacts
	public static boolean updateContact(ContactDetails contact) {
		ContactDetails cd = new ContactDetails();
		cd.setContactId(contact.getContactId());
	    QueryGenerator qg = new QueryGenerator();
		int result = qg.update(contact,cd);
		return result > 0;
	}
	
}