package com.pkg.CategoryServletPkg;

import java.io.IOException;




import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.pkg.POJO.Categories;
import com.pkg.POJO.CategoryMap;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;

@WebServlet("/AddCategoryServlet")
public class AddCategoryServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String categoryName = request.getParameter("categoryName");
		
		PrintWriter out = response.getWriter();
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserID();
		String[] selectedContacts = request.getParameterValues("selectedContacts");

		
		Categories category = new Categories();
		category.setCategoryName(categoryName);
		category.setUserID(userId);
		
        List<ContactDetails> contacts = new ArrayList<>();
        if (selectedContacts != null) {
            for (String contactIdStr : selectedContacts) {
            	ContactDetails contact = new ContactDetails();
                contact.setContactId(Integer.parseInt(contactIdStr));
                contacts.add(contact); // Add contact to the list
            }
        }
		try {
			int categoryId = CategoryDao.addCategory(category);
			if(categoryId > 0) {
				category.setCategoryID(categoryId);
				
				
				if(!contacts.isEmpty()) {
					for (ContactDetails contact : contacts) {
						CategoryMap categoryMap = new CategoryMap();
						categoryMap.setCategoryID(categoryId);
						categoryMap.setContactID(contact.getContactId());
                        CategoryDao.addContactToCategory( categoryMap);
                    }
					
				}
				else {
					
					out.println("<script type='text/javascript'>");
					out.println("alert('category added !');");
					out.println("window.location.href = 'Categories.jsp';"); 
					out.println("</script>");
					return ;
				}
				out.println("<script type='text/javascript'>");
				out.println("alert('category added with members!');");
				out.println("window.location.href = 'Categories.jsp';"); 
				out.println("</script>");
				return ;
				
			}
			else {
				out.println("<script type='text/javascript'>");
				out.println("alert('Oops ! Something went wrong.');");
				out.println("window.location.href = 'Categories.jsp';"); 
				out.println("</script>");
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
