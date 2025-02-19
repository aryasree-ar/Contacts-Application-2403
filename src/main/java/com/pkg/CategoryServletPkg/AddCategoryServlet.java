package com.pkg.CategoryServletPkg;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pkg.POJO.Categories;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.UserSessionCache;

import com.pkg.Filters.AuthFilter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;

@WebServlet("/AddCategory")
public class AddCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String categoryName = request.getParameter("categoryName");
		String sessionId = AuthFilter.SESSION_ID.get();
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserId();
		String[] selectedContacts = request.getParameterValues("selectedContacts");

		Categories category = new Categories();
		category.setCategoryName(categoryName);
		category.setUserId(userId);

		List<ContactDetails> contacts = new ArrayList<>();
		if (selectedContacts != null && selectedContacts.length > 0) {
			for (String contactIdStr : selectedContacts) {
				ContactDetails contact = new ContactDetails();
				contact.setContactId(Integer.parseInt(contactIdStr));
				contacts.add(contact);
			}
		}
		
			int categoryId = 0;
			try {
				categoryId = CategoryDao.addCategory(category);
			} catch (SQLException | NamingException e) {
				response.sendRedirect("categories.jsp?message=Something went wrong");
			}
			if (categoryId > 0) {
				category.setCategoryId(categoryId);
				if (!contacts.isEmpty()) {
					for (ContactDetails contact : contacts) {
						CategoryMap categoryMap = new CategoryMap();
						categoryMap.setCategoryId(categoryId);
						categoryMap.setContactId(contact.getContactId());
						try {
							CategoryDao.addContactToCategory(categoryMap);
						} catch (SQLException | NamingException e) {
							response.sendRedirect("categories.jsp?message=Something went wrong");
						}
					}
				}
			}
			response.sendRedirect("categories.jsp");
			return;
		
	}
}


