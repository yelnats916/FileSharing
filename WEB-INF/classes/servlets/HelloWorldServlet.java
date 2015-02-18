package servlets;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class HelloWorldServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
	
	try {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley"); 
		Statement stmt = con.createStatement();
		String sql;
		sql = "insert into Users values('" + username + "','" + password + "')";
		int rs = stmt.executeUpdate(sql);
 		
		//rs.close();
		stmt.close();
		con.close();
		
		request.setAttribute("message", "exmessage");
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	} catch (Exception ex) {
		throw new ServletException(ex);
	}
 
    }

}
