package com.pkg.CategoryServletPkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;


@WebServlet("/RemoveMember")
public class RemoveMemberServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("categoryName"); 
		int contactId = Integer.parseInt(request.getParameter("contactId"));
		try {
			CategoryDao.removeMemberFromCategory(contactId);
			request.setAttribute("categoryId", categoryId);
			request.setAttribute("categoryName", categoryName);
			request.getRequestDispatcher("categoryUpdate.jsp").forward(request, response);
			return ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
