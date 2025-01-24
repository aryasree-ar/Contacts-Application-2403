package com.pkg.UserServletPkg;

//import com.pkg.Util.ObjectHash;

import java.io.IOException;




import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import com.pkg.Dao.SessionDao;
import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.Util.PasswordUtil;
import com.pkg.sessionUtil.SessionIdGenerator;
import com.pkg.sessionUtil.UserSessionCache;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
       
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		user.setUserName(request.getParameter("sign-up-name"));
		user.setUserPassword(PasswordUtil.hashPassword(request.getParameter("sign-up-password")));
		user.setUserFirstName(request.getParameter("sign-up-frist-name"));
		user.setUserLastName(request.getParameter("sign-up-last-name"));
		user.setUserPhone(request.getParameter("sign-up-phone-number"));
		user.setUserDOB(request.getParameter("sign-up-dob"));
		user.setUserLocation(request.getParameter("sign-up-location"));
		
		PrintWriter out = response.getWriter(); 
		
		List<UserEmails> userEmails = new ArrayList<>();
		String primaryEmail = request.getParameter("sign-up-email");
		userEmails.add(new UserEmails(primaryEmail, 1));
		
		user.setUserMails(userEmails);
		
		//String hashedPassWord = BCrypt.hashpw(userPassword, BCrypt.gensalt());
		
		//to check for duplicacy in mails
		try {
			if(UserDao.checkDuplicateMail(primaryEmail)) {
				
				out.println("<script type='text/javascript'>");
				out.println("alert('Oops ! User mail already taken');");
				out.println("window.location.href = 'signUp.jsp';"); 
				out.println("</script>");
				return ;
			} 
			else {
				int result = UserDao.insertUserDetails(user,1);
				if (result >0) {

					String sessionId = SessionIdGenerator.getSessionId();
					UserSessions userSession = new UserSessions(sessionId, result, Instant.now().toEpochMilli(), Instant.now().toEpochMilli());
					//adding the session to the cache and db
					UserSessionCache.addSessionToCache(userSession);
					SessionDao.createSession(userSession);
					
					//setting in cookie
					Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
					sessionCookie.setHttpOnly(true);
					response.addCookie(sessionCookie);
					
					//ObjectHash.userObj.put(result, user);
					
					out.println("<script type='text/javascript'>");
					out.println("alert('Successfully registered ! Click OK to continue');");
					out.println("window.location.href = 'dashboard.jsp';"); 
					out.println("</script>");
					
				}
				else {
					out.println("<script type='text/javascript'>");
					out.println("alert('Try again !');");
					out.println("window.location.href = 'signUp.jsp';"); 
					out.println("</script>");
				}
				
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} 


	}

}
