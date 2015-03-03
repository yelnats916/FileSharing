package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SignupServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	
	try {
		String msgOutput;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordCopy = request.getParameter("passwordCopy");
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley"); 
		PreparedStatement stmt = 
         con.prepareStatement("SELECT * from Users where username=?");

      if (username.isEmpty() || password.isEmpty() || passwordCopy.isEmpty()) {
         msgOutput = "Please fill in empty fields";
      } else if (!passwordCopy.equals(password)) {
			msgOutput = "Passwords do not match";
		} else if (username.length() < 5 || password.length() < 5) {
         msgOutput = "Username and password must have a minimum length of 5";
      } else {
         stmt.setString(1, username);
         ResultSet rs = stmt.executeQuery();
         
         if (rs.first()) {
            msgOutput = "Username already taken";
         } else {
            String hash = PasswordHash.createHash(password);
            stmt = con.prepareStatement("insert into Users values(?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, hash);
            stmt.executeUpdate();
            msgOutput = "Sign up successful!" + " " + hash;
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
