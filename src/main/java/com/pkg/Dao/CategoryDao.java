package com.pkg.Dao;

import java.sql.*;



import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.pkg.POJO.Categories;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.Util.DBConnection;
import com.pkg.queryGenerator.QueryGenerator;

public class CategoryDao {
	static String query ;
	
	//creating category
	public static int addCategory(Categories category) throws SQLException, NamingException {
		int categoryId = -1;
		try {
			QueryGenerator queryGenerator = new QueryGenerator();
			categoryId = queryGenerator.insert(category);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return categoryId;
	}
	

	//adding contacts to the category
	public static void addContactToCategory(CategoryMap categoryMap) throws SQLException, NamingException {
		try {
			QueryGenerator queryGenerator = new QueryGenerator();
			int result = queryGenerator.insert(categoryMap);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<Categories> getCategories(int userId) {
		query = "select * from categories where userID = ?";
		List<Categories> categories = new ArrayList<>();
		try(Connection c = DBConnection.connect();
	             PreparedStatement p = c.prepareStatement(query))  {
			p.setInt(1, userId);
			try(ResultSet rs = p.executeQuery()){
				while(rs.next()) {
					Categories category = new Categories();
					category.setCategoryID(rs.getInt("categoryID"));
					category.setCategoryName(rs.getString("categoryName"));
					category.setUserID(rs.getInt("userID"));
					categories.add(category);
				}
			}
			return categories;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//getting the members of the category
	public static List<ContactDetails> getCategoryMembers(int categoryId) {
		query = "select c.contactID, c.nickName from contactDetails c join categoryMap cm on c.contactID = cm.contactID where cm.categoryID=?";
		List<ContactDetails> members = new ArrayList<>();
		try (Connection c = DBConnection.connect();
	             PreparedStatement p = c.prepareStatement(query)) {
			p.setInt(1, categoryId);
			try(ResultSet rs = p.executeQuery()){
				while(rs.next()) {
					ContactDetails contact = new ContactDetails();
					contact.setContactId(rs.getInt("contactID"));
					contact.setNickName(rs.getString("nickName"));
					members.add(contact);
				}
				return members;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//getting members not in the group
	public static List<ContactDetails> getNonCategoryMembers(int userID, int categoryID) {
		query="select contactID, nickName from contactDetails where userID = ? and contactID not in (select contactID from categoryMap where categoryID = ?)";
		List<ContactDetails> nonMembers = new ArrayList<>();
		try(Connection c = DBConnection.connect();
	             PreparedStatement p = c.prepareStatement(query)) {
			p.setInt(1, userID);
			p.setInt(2, categoryID);
			try(ResultSet rs = p.executeQuery()){
				while(rs.next()) {
					ContactDetails contact = new ContactDetails();
					contact.setContactId(rs.getInt("contactID"));
					contact.setNickName(rs.getString("nickName"));
					nonMembers.add(contact);
				}
				return nonMembers;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//removing member from a category
	public static void removeMember(int contactId) throws IllegalArgumentException, IllegalAccessException {
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setContactID(contactId);
		if(categoryMapObj != null) {
			QueryGenerator queryGenerator = new QueryGenerator();
			int result = queryGenerator.delete(categoryMapObj);
			if(result > 0) {
				System.out.println("member removed" + result);
			}
		}
	}
	//remove category
	public static boolean deleteCategory(int categoryId) throws IllegalArgumentException, IllegalAccessException  {
		
		CategoryMap categoryMapObj = new CategoryMap();
		categoryMapObj.setCategoryID(categoryId);
		
		System.out.println(categoryMapObj);
		if(categoryMapObj != null) {
			QueryGenerator queryGenerator1 = new QueryGenerator();
			if(queryGenerator1.delete(categoryMapObj) > 0) {
				
				Categories categoriesObj = new Categories();
				categoriesObj.setCategoryID(categoryId);
				
				if(categoriesObj != null) {
					QueryGenerator queryGenerator2 = new QueryGenerator();
					if(queryGenerator2.delete(categoriesObj)>0)return true;
					else { 
						System.out.println("categories not deleted");
						return false;
					}
					
				}
				
			}
			else {
				System.out.println("category Map is not deleted");
				return false;
			}
		}
		return false;

	}
	
}
