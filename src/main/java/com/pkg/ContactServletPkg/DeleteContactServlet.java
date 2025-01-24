package com.pkg.ContactServletPkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.ContactsDao;

@WebServlet("/DeleteContactServlet")
public class DeleteContactServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int contactId = Integer.parseInt(request.getParameter("contactId"));
		PrintWriter out = response.getWriter();
		System.out.println(contactId);
		try {
			if(ContactsDao.deleteContact(contactId)) {
				response.sendRedirect("MyContacts.jsp");
				System.out.println("inside if");
			}
			else {
				System.out.println("else");
				out.println("<script type='text/javascript'>");
				out.println("alert('Oops ! That did'nt work...');");
				out.println("window.location.href = 'MyContacts.jsp';"); 
				out.println("</script>");
				
			}
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
