package com.pkg.CategoryServletPkg;

import java.io.IOException;


import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;
import com.pkg.POJO.CategoryMap;


@WebServlet("/AddContactsToCategoryServlet")
public class AddContactsToCategoryServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("categoryName"); 
		String[] selectedContacts = request.getParameterValues("selectedContacts");
		PrintWriter out = response.getWriter();
		try {
			if (categoryId > 0 && selectedContacts != null) {
                
                for (String contactIdStr : selectedContacts) {
                    int contactId = Integer.parseInt(contactIdStr);
                    
                    CategoryMap categoryMap = new CategoryMap();
                    categoryMap.setCategoryID(categoryId);
                    categoryMap.setContactID(contactId);
                    
                    // Call the DAO method to add the contact to the category using the CategoryMap POJO
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
