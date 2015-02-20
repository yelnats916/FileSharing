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
      Statement stmt = con.createStatement();

		String msgOutput;
	   
      if (username.isEmpty() || password.isEmpty()) {
         msgOutput = "Please fill in empty fields";
      } else {
         String sql = "SELECT hash from Users where username='" + username + "'";
         ResultSet rs = stmt.executeQuery(sql);

         boolean validLogin = false;

         if (rs.next()) {
            String hash = rs.getString("hash");
            validLogin = PasswordHash.validatePassword(password, hash);
         }

         if (validLogin) {
            msgOutput = "SUCCESS";
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
