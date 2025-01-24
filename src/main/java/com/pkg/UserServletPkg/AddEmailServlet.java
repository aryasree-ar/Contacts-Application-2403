package com.pkg.UserServletPkg;

import java.io.IOException;





import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/AddEmailServlet")
public class AddEmailServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		
		PrintWriter out = response.getWriter();
		int userId = userSession.getUserID(); 
		String mailId = request.getParameter("addemail");
		try {
			if(!UserDao.checkDuplicateMail(mailId)) {
				if (UserDao.addUserEmail(userId, mailId)) {
					User user = UserDao.getUserById(userId);
					if(user != null) {
						UserEmails newMail = new UserEmails(mailId, 0) ;
						user.getUserMails().add(newMail);
					}
					response.sendRedirect("Profile.jsp");
				}
				else {
					System.out.println("Didnt add user email");
				}
			}
			else {
				out.println("<script type='text/javascript'>");
				out.println("alert('Oops ! User mail already taken');");
				out.println("window.location.href = 'Profile.jsp';"); 
				out.println("</script>");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
