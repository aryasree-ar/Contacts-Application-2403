package com.pkg.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.Categories;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.queryGenerator.QueryExecutor;

public class CategoryDao {

	public static int addCategory(Categories category) throws SQLException, NamingException {
		int categoryId = -1;
		try {
			QueryExecutor queryExecutorObject = new QueryExecutor();
			boolean getGeneratedId = true;
			categoryId = queryExecutorObject.executeInsert(category, getGeneratedId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryId;
	}

	public static boolean addContactToCategory(CategoryMap categoryMap) throws SQLException, NamingException {
		try {
			QueryExecutor queryExecutorObject = new QueryExecutor();
			boolean getGeneratedId = false;
			return queryExecutorObject.executeInsert(categoryMap, getGeneratedId) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<Categories> getCategories(int userId) throws InvalidInputException, DBException {
		Categories categoriesObject = new Categories();
		categoriesObject.setUserId(userId);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		List<Categories> categories = queryExecutorObject.executeSelect(categoriesObject).stream()
				.filter(Categories.class::isInstance).map(Categories.class::cast).toList();

		return categories;
	}

	public static List<ContactDetails> getCategoryMembers(int catergoryId) throws InvalidInputException, DBException {
		CategoryMap categoryMapObject = new CategoryMap();
		categoryMapObject.setCategoryId(catergoryId);

		QueryExecutor queryExecutorObject = new QueryExecutor();
		List<CategoryMap> categoryMapList = queryExecutorObject.executeSelect(categoryMapObject).stream()
				.filter(CategoryMap.class::isInstance).map(CategoryMap.class::cast).toList();

		List<ContactDetails> contactDetailsList = new ArrayList<>();
		for (CategoryMap categoryMap : categoryMapList) {
			ContactDetails contactDetailsObject = new ContactDetails();
			contactDetailsObject.setContactId(categoryMap.getContactId());
			List<ContactDetails> contactDetail = queryExecutorObject.executeSelect(contactDetailsObject).stream()
					.filter(ContactDetails.class::isInstance).map(ContactDetails.class::cast).toList();
			contactDetailsList.add(contactDetail.get(0));
		}
		return contactDetailsList;
	}

	public static List<ContactDetails> getNonCategoryMembers(int userID, int categoryID) throws InvalidInputException, DBException {
		CategoryMap categoryMapObject = new CategoryMap();
		categoryMapObject.setCategoryId(categoryID);

		QueryExecutor queryExecutorObject = new QueryExecutor();
		List<Integer> categoryMembersIdList = queryExecutorObject.executeSelect(categoryMapObject).stream()
				.filter(CategoryMap.class::isInstance).map(CategoryMap.class::cast).map(CategoryMap::getContactId)
				.toList();

		ContactDetails contactDetails = new ContactDetails();
		contactDetails.setUserId(userID);
		List<ContactDetails> contactList = queryExecutorObject.executeSelect(contactDetails).stream()
				.filter(ContactDetails.class::isInstance).map(ContactDetails.class::cast).toList();

		List<ContactDetails> nonCategoryMembersList = contactList.stream()
				.filter(contact -> !categoryMembersIdList.contains(contact.getContactId())).toList();
		return nonCategoryMembersList;
	}

	public static void removeMemberFromCategory(int contactId) throws IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setContactId(contactId);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		queryExecutorObject.executeDelete(categoryMapObj);
	}

	public static boolean deleteCategory(int categoryId) throws IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setCategoryId(categoryId);
		boolean status = false;
		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeDelete(categoryMapObj) >= 0) {
			Categories categoriesObj = new Categories();
			categoriesObj.setCategoryId(categoryId);
			if (queryExecutorObject.executeDelete(categoriesObj) > 0) {
				status = true;
			}
		}
		return status;
	}
}
