package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
      try {

      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley");
      PreparedStatement stmt = 
         con.prepareStatement("SELECT hash from Users where username=?");

		String msgOutput;
	   
      if (username.isEmpty() || password.isEmpty()) {
         msgOutput = "Please fill in empty fields";
      } else {
         stmt.setString(1, username);
         ResultSet rs = stmt.executeQuery();

         boolean validLogin = false;

         if (rs.next()) {
            String hash = rs.getString("hash");
            validLogin = PasswordHash.validatePassword(password, hash);
         }

         if (validLogin) {
            msgOutput = "SUCCESS";
            Cookie cookie = new Cookie("FileShareSite", username);
            cookie.setMaxAge(60);
            cookie.setDomain("ec2-54-153-35-253.us-west-1.compute.amazonaws.com");
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            response.sendRedirect("listing?" + "user=" + username);
            return;            
         } else {
            msgOutput = "Invalid username or password";
         }
      }
		
      stmt.close();
      con.close();
      request.setAttribute("message", msgOutput);
      request.getRequestDispatcher("/login.jsp").forward(request, response);

      } catch (Exception ex) {
         throw new ServletException(ex);
      }
      
	}
}
