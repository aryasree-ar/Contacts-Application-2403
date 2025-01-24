package com.pkg.Dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import org.mindrot.jbcrypt.BCrypt;

import com.pkg.POJO.DbPojo;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserDetails;
import com.pkg.Util.DBConnection;
import com.pkg.Util.PasswordUtil;
import com.pkg.queryGenerator.QueryGenerator;

public class UserDao {
	static String query;
	
	//method to get user details by ID
	public static User getUserById(int userId) {
		query = "select * from userEmails where userID = ?";
		List<UserEmails> userEmailsList = new ArrayList<>(); 
		try(Connection c = DBConnection.connect();
				PreparedStatement p = c.prepareStatement(query)){
			p.setInt(1, userId);
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				userEmailsList.add(new UserEmails(rs.getString("email"), rs.getInt("isPrimary")));
			}
			
			String userQuery = "select * from userLoginDetails where userID = ?";
			try (PreparedStatement p2 = c.prepareStatement(userQuery)) {
	            p2.setInt(1, userId);
	            ResultSet rs1 = p2.executeQuery();
	            if (rs1.next()) {
	                User user = new User();
	                user.setUserId(rs1.getInt("userID"));
	                user.setUserName(rs1.getString("user_name")); // Ensure the case matches
	                user.setUserFirstName(rs1.getString("first_name"));
	                user.setUserLastName(rs1.getString("last_name"));
	                user.setUserPhone(rs1.getString("phone_number"));
	                user.setUserDOB(rs1.getString("dob"));
	                user.setUserLocation(rs1.getString("location"));
	                user.setUserMails(userEmailsList);
	                
	                return user;
	            }
	        }
			catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//to check duplicate email
	public static boolean checkDuplicateMail(String mail) throws SQLException, NamingException {
		
//		query = "select email from userEmails where email = ?";
//		try (Connection c = DBConnection.connect();
//			PreparedStatement p = c.prepareStatement(query)){
//			
//			
//			p.setString(1, mail);
//			ResultSet rs = p.executeQuery();
//			//check if there is a duplicate mail
//			return rs.next();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("checking for duplicate mail");
		QueryGenerator queryGenerator = new QueryGenerator();
		UserEmails obj = new UserEmails();
		obj.setEmail(mail);
		List<DbPojo> resultSet = queryGenerator.select(obj);
		System.out.println(resultSet.size());
		return resultSet.size() > 0;
		
	}
	
	//to insert signUp details and return userId
	public static int insertUserDetails(User user,int hashCode) {

		try {
			UserDetails userObject = new UserDetails(user.getUserId(), user.getUserName(), user.getUserPassword(), user.getUserFirstName(), user.getUserLastName(), user.getUserPhone(), user.getUserDOB(), user.getUserLocation(), hashCode);
			QueryGenerator queryGenerator = new QueryGenerator();
			int userId = queryGenerator.insert(userObject);
			List<UserEmails> userEmailsList = user.getUserMails();
			
			try {
				UserEmails userEmail = new UserEmails(userEmailsList.get(0).getEmail(), 1);
				userEmail.setUserId(userId);
				QueryGenerator queryGenerator2 = new QueryGenerator();
				if(queryGenerator2.insert(userEmail) > 0) {
					return userId;
				}
				return userId;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	//login logic
	public static int loginUser(String userMail, String password) {
		query = "select * from userLoginDetails ld left join userEmails um on ld.userID = um.userID where email=?";
		try (Connection c = DBConnection.connect();
	             PreparedStatement p = c.prepareStatement(query)) {
			p.setString(1, userMail);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt("userID");
				String hashedPassword = rs.getString("password");
				int hashCode = rs.getInt("hash_code");
				//BCrypt.checkpw(password, hashedPassword)
				if (hashCode == 0) {
					if (BCrypt.checkpw(password, hashedPassword)) {
						 String newHashedPassword = PasswordUtil.hashPassword(password);
					     updateUserPassword(userId, newHashedPassword, 1); 
					     return userId;
	                }
				}
				else {
					 if (PasswordUtil.checkPassword(password, hashedPassword)) {
		                    return userId;
		                }
				}
				return -1;
			}
			else {
				return -2;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//to update the scrypt passwords
	public static void updateUserPassword(int userId, String newHashedPassword , int hashCode) {

	    UserDetails user = new UserDetails();
	    user.setPassword(newHashedPassword);
	    user.setHash_code(hashCode);
	    user.setUserID(userId);
	    
	    UserDetails u = new UserDetails();
	    u.setUserID(userId);
	    
	    QueryGenerator qg = new QueryGenerator();
	    int result = qg.update(user,u);
	   
	    
	}

	// to add mails in the profile 
	public static boolean addUserEmail(int userId, String mailId) throws ClassNotFoundException, SQLException, NamingException {

		boolean result = false;
		UserEmails userEmailsObject = new UserEmails(mailId, 0);
		userEmailsObject.setUserId(userId);
		try {
			QueryGenerator queryGenerator = new QueryGenerator();
			if(queryGenerator != null) {
			result = queryGenerator.insert(userEmailsObject) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	
	public static void deleteUser(int userId) throws IllegalArgumentException, IllegalAccessException {
		User user = getUserById(userId);
		if(user!=null) {
			QueryGenerator queryGenerator = new QueryGenerator();
			UserDetails delObj = new UserDetails();
			delObj.setUserID(userId);
			queryGenerator.delete(delObj);
		}
	}
	
	// to delete user mails
	public static boolean deleteUserEmail(int userId,String email) throws IllegalArgumentException, IllegalAccessException {
		UserEmails userEmail = new UserEmails();
		userEmail.setEmail(email);
		userEmail.setUserId(userId);
		if(userEmail != null) {
			QueryGenerator queryGenerator = new QueryGenerator();
			if(queryGenerator.delete(userEmail) > 0) return true;
		}
		
		

		return false;
		
		
	}
	
	//to set primary mail
	public static boolean setPrimaryEmail(int userId, String email) {
		int result= 0;
		
		QueryGenerator queryGenerator = new QueryGenerator();
		
		UserEmails u1 = new UserEmails();
		UserEmails conditionObj1 = new UserEmails();
		
		u1.setIsPrimary(0);
		conditionObj1.setUserId(userId);
		conditionObj1.setIsPrimary(1);
		result = queryGenerator.update(u1, conditionObj1);
		
		u1.setIsPrimary(1);
		UserEmails conditionObj2 = new UserEmails();
		conditionObj2.setUserId(userId);
		conditionObj2.setEmail(email);
		result = queryGenerator.update(u1, conditionObj2);
		
		return result > 0;
	}
	
}
