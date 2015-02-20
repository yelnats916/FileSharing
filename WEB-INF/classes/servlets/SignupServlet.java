package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class SignupServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	
	try {
      String sql;
		String msgOutput;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordCopy = request.getParameter("passwordCopy");
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley"); 
		Statement stmt = con.createStatement();

      if (username.isEmpty() || password.isEmpty() || passwordCopy.isEmpty()) {
         msgOutput = "Please fill in empty fields";
      } else if (!passwordCopy.equals(password)) {
			msgOutput = "Passwords do not match";
		} else {
		   sql = "SELECT * from Users where username='" + username + "'";
         ResultSet rs = stmt.executeQuery(sql);
         
         if (rs.first()) {
            msgOutput = "Username already taken";
         } else {
            sql = "insert into Users values('" + username + "','" + password + "')";
            stmt.executeUpdate(sql);
            msgOutput = "Sign up successful!";
         }
      }
 		
		stmt.close();
		con.close();
		
		request.setAttribute("message", msgOutput);
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
   } catch (Exception ex) {
		throw new ServletException(ex);
	}
 
   } // end of POST method

}
