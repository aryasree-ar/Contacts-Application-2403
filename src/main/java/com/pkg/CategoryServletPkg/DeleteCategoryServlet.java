package com.pkg.CategoryServletPkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.CategoryDao;


@WebServlet("/DeleteCategoryServlet")
public class DeleteCategoryServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		PrintWriter out = response.getWriter(); 
		try {
			if(CategoryDao.deleteCategory(categoryId)) {
				response.sendRedirect("Categories.jsp");
			}
			else {
				response.sendRedirect("Categories.jsp");
				System.out.println("category is not deleted");
			}
			

			return ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
