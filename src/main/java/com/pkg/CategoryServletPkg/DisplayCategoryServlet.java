package com.pkg.CategoryServletPkg;

import java.io.IOException;

import com.pkg.Dao.CategoryDao;
import com.pkg.Dao.ContactsDao;
import com.pkg.POJO.Categories;
import com.pkg.POJO.ContactDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/DisplayCategoryServlet")
public class DisplayCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request);
		//user_sessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = UserSessionCache.getUserIdFromSessionCache(sessionId);
		List<Categories> categories = CategoryDao.getCategories(userId);
		List<ContactDetails> contactList = ContactsDao.getContacts(userId);
		request.setAttribute("categories", categories);
		request.setAttribute("contactList", contactList);
		request.getRequestDispatcher("Categories.jsp").forward(request, response);
	}
}