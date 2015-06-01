package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class LogoutServlet extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException, ServletException {
      Cookie cookie = new Cookie("FileShareSite", "");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
      response.sendRedirect("login.jsp");
   }
}
