package com.pkg.CategoryServletPkg;

import java.io.IOException;




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;
import com.pkg.POJO.CategoryMap;


@WebServlet("/AddContactsToCategory")
public class AddContactsToCategoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("categoryName"); 
		String[] selectedContacts = request.getParameterValues("selectedContacts");
		try {
			if (categoryId > 0 && selectedContacts != null && selectedContacts.length > 0) {
                for (String contactIdStr : selectedContacts) {
                    int contactId = Integer.parseInt(contactIdStr);
                    CategoryMap categoryMap = new CategoryMap();
                    categoryMap.setCategoryId(categoryId);
                    categoryMap.setContactId(contactId);
                    CategoryDao.addContactToCategory(categoryMap);
                }
                request.setAttribute("categoryId", categoryId);
    			request.setAttribute("categoryName", categoryName);
    			request.getRequestDispatcher("categoryUpdate.jsp").forward(request, response);
				return ;
			}
			else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
