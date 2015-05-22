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

public class AddFriendServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	
	try {
		String user = request.getParameter("user");
      String friend = request.getParameter("friend");
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley"); 
		PreparedStatement stmt = 
         con.prepareStatement("insert into Friends values(?, ?)");
      stmt.setString(1, user);
      stmt.setString(2, friend);
      stmt.executeUpdate();

		stmt.close();
		con.close();

      response.sendRedirect("listing?" + "user=" + user);
   } catch (Exception ex) {
		throw new ServletException(ex);
	}
 
   } // end of POST method

}
