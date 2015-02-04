package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username="user";
		String password="root";
		
		String un = request.getParameter("username");
		String pw = request.getParameter("password");
		
		String msg;
		
		if (un.equals(username) && pw.equals(password)) {
			msg = "Hello " + un + "! Your login is successful.";
		} else {
			msg = "Invalid username or password.";
			
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<font size='6' color=red>" + msg + "</font>");
	}
}