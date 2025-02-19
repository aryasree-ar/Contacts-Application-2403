package com.pkg.CategoryServletPkg;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;


@WebServlet("/DeleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		try {
			CategoryDao.deleteCategory(categoryId);
			response.sendRedirect("categories.jsp");
			return ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
