package com.pkg.queryGenerator;

import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserEmails;

public class Test {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		QueryGenerator qg = new QueryGenerator();
//		contactDetails cd = new contactDetails();
//		cd.setUserId(16);
//		qg.select(cd);
		
		ContactDetails cd = new ContactDetails();
		//cd.setContactId(5);
		cd.setGender("Female");
		ContactDetails cd1 = new ContactDetails();
		cd1.setUserId(17);
		//
		qg.update(cd,cd1);
	}
}
