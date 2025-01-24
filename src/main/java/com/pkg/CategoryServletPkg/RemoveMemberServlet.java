package com.pkg.CategoryServletPkg;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;


@WebServlet("/RemoveMemberServlet")
public class RemoveMemberServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("categoryName"); 
		int contactId = Integer.parseInt(request.getParameter("contactId"));
//		CategoryMap categoryMap = new CategoryMap();
//		categoryMap.setCategoryId(categoryId);
//		categoryMap.setContactId(contactId);
		PrintWriter out = response.getWriter();
		try {
			CategoryDao.removeMember(contactId);
			request.setAttribute("categoryId", categoryId);
			request.setAttribute("categoryName", categoryName);
			request.getRequestDispatcher("categoryUpdate.jsp").forward(request, response);

			//response.sendRedirect("categoryUpdate.jsp");

			return ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
