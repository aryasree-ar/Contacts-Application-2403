package com.pkg.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import org.mindrot.jbcrypt.BCrypt;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.DbPojo;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserDetails;
import com.pkg.Util.PasswordUtil;
import com.pkg.queryGenerator.QueryExecutor;

public class UserDao {

	public static User getUserById(int userId) throws InvalidInputException, DBException {
		if (userId <= 0) {
			throw new InvalidInputException("Invalide user credentials. Please try again.");
		}
		QueryExecutor queryExecutorObject = new QueryExecutor();
		UserDetails userDetails = new UserDetails();
		userDetails.setUserId(userId);
		List<DbPojo> userDetailsObjectList = queryExecutorObject.executeSelect(userDetails);
		if (userDetailsObjectList == null || userDetailsObjectList.isEmpty()) {
			throw new InvalidInputException("No user found with the given credentials");
		}

		UserEmails userEmailObject = new UserEmails();
		userEmailObject.setUserId(userId);
		List<UserEmails> userEmailList = queryExecutorObject.executeSelect(userEmailObject).stream()
				.filter(UserEmails.class::isInstance).map(UserEmails.class::cast).toList();
		if (userEmailList == null || userEmailList.isEmpty()) {
			throw new InvalidInputException("User must have atleast one email.");
		}
		User userObject = new User();

		UserDetails userDetailsObject = (UserDetails) userDetailsObjectList.get(0);
		userObject.setUserId(userDetailsObject.getUserId());
		userObject.setUserName(userDetailsObject.getUserName());
		userObject.setUserFirstName(userDetailsObject.getFirstName());
		userObject.setUserLastName(userDetailsObject.getLastName());
		userObject.setUserPhone(userDetailsObject.getPhoneNumber());
		userObject.setUserDOB(userDetailsObject.getDob());
		userObject.setUserLocation(userDetailsObject.getLocation());
		userObject.setUserMails(new ArrayList<>(userEmailList));
		return userObject;
	}

	public static boolean checkDuplicateMail(String mail) throws InvalidInputException, SQLException, NamingException, DBException {
		if (mail == null || mail.trim().isEmpty()) {
			throw new InvalidInputException("Email cannot be empty.");
		}
		QueryExecutor queryExecutorObject = new QueryExecutor();
		UserEmails userEmailsObject = new UserEmails();
		userEmailsObject.setEmail(mail);
		List<DbPojo> resultSet = queryExecutorObject.executeSelect(userEmailsObject);
		return resultSet.size() > 0;
	}

	public static int insertUserDetails(User user, int hashCode) throws InvalidInputException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, DBException  {
		if (user == null) {
			throw new InvalidInputException("Something went worng! Please try again.");
		}
		UserDetails userObject = new UserDetails(user.getUserId(), user.getUserName(), user.getUserPassword(),
				user.getUserFirstName(), user.getUserLastName(), user.getUserPhone(), user.getUserDOB(),
				user.getUserLocation(), hashCode);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		int userId = queryExecutorObject.executeInsert(userObject, true);

		if (user.getUserMails() == null || user.getUserMails().isEmpty()) {
			throw new InvalidInputException("User must have atleast one email.");
		}
		List<UserEmails> userEmailsList = user.getUserMails();
		UserEmails userEmail = new UserEmails(userEmailsList.get(0).getEmail(), 1);
		userEmail.setUserId(userId);
		if (queryExecutorObject.executeInsert(userEmail, false) <= 0) {
			throw new DBException("Oops! That did not work.");
		}
		return userId;
	}

	public static int loginUser(String userMail, String password) throws InvalidInputException, DBException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		QueryExecutor queryExecutorObject = new QueryExecutor();
		UserEmails userEmailsObject = new UserEmails();
		userEmailsObject.setEmail(userMail);

		List<UserEmails> userEmailsList = queryExecutorObject.executeSelect(userEmailsObject).stream()
				.filter(UserEmails.class::isInstance).map(UserEmails.class::cast).toList();

		if (userEmailsList == null || userEmailsList.isEmpty()) {
			throw new InvalidInputException("No account found with this email.");
		}

		int userId = userEmailsList.get(0).getUserId();
		UserDetails user = new UserDetails();
		user.setUserId(userId);

		UserDetails userDetails = queryExecutorObject.executeSelect(user).stream().filter(UserDetails.class::isInstance)
				.map(UserDetails.class::cast).toList().get(0);

		String hashedPassword = userDetails.getPassword();
		int passwordVersion = userDetails.getPasswordVersion();

		if (passwordVersion == 0) {
			if (!BCrypt.checkpw(password, hashedPassword)) {
				throw new InvalidInputException("Incorrect password");
			}
			String newHashedPassword = PasswordUtil.hashPassword(password);
			updateUserPassword(userId, newHashedPassword, 1);
		} else {
			if (!PasswordUtil.checkPassword(password, hashedPassword)) {
				throw new InvalidInputException("Incorrect password");
			}
		}
		return userId;
	}

	public static void updateUserPassword(int userId, String newHashedPassword, int hashCode) throws InvalidInputException, DBException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		if (userId <= 0) {
			throw new InvalidInputException("Invalid user. Please try again.");
		}
		if (newHashedPassword == null || newHashedPassword.isEmpty()) {
			throw new InvalidInputException("Password cannot be empty.");
		}
		UserDetails user = new UserDetails();
		user.setPassword(newHashedPassword);
		user.setPasswordVersion(hashCode);
		user.setUserId(userId);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeUpdate(user) < 1) {
			throw new DBException("Oops! That did not work. Please try again.");
		}
	}

	public static boolean addUserEmail(int userId, String mailId) throws InvalidInputException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, DBException {
		if (userId <= 0 || mailId == null || mailId.isEmpty()) {
			throw new InvalidInputException("Invalid email credentials.");
		}
		UserEmails userEmailsObject = new UserEmails(mailId, 0);
		userEmailsObject.setUserId(userId);
		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeInsert(userEmailsObject, false) <= 0) {
			throw new DBException("Oops! That did not work. Please try again.");
		}
		return true;
	}

	public static boolean deleteUserEmail(int userId, String email) throws InvalidInputException, DBException  {
		if (userId <= 0 || email == null || email.isEmpty()) {
			throw new InvalidInputException("Invalid email credentials. Please try again.");
		}
		UserEmails userEmail = new UserEmails();
		userEmail.setEmail(email);
		userEmail.setUserId(userId);

		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeDelete(userEmail) <= 0) {
			throw new DBException("Oops ! That did not work. Please try again.");
		}
		return true;

	}

	public static boolean setPrimaryEmail(int userId, String email) throws InvalidInputException, DBException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		if (userId <= 0 || email == null || email.isEmpty()) {
			throw new InvalidInputException("Invalid email credentials. Please try again.");
		}

		QueryExecutor queryExecutorObject = new QueryExecutor();
		UserEmails u1 = new UserEmails();

		u1.setIsPrimary(0);
		u1.setUserId(userId);
		u1.setEmail(getUserById(userId).getPrimaryMail().getEmail());
		if (queryExecutorObject.executeUpdate(u1) <= 0) {
			throw new DBException("Oops! That did not work. Please try again.");
		}

		u1.setIsPrimary(1);
		u1.setEmail(email);
		if (queryExecutorObject.executeUpdate(u1) <= 0) {
			throw new DBException("Oops! That did not work. Please try again.");
		}
		return true;
	}

}
