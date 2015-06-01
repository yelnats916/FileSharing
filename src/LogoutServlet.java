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

   private static String DOMAIN = ".ec2-54-153-35-253.us-west-1.compute.amazonaws.com";

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException, ServletException {
      Cookie killMyCookie = new Cookie("FileShareSite", null);
      killMyCookie.setMaxAge(0);
      killMyCookie.setPath("/");            
      killMyCookie.setDomain(DOMAIN);
      response.addCookie(killMyCookie);
      response.sendRedirect("login.jsp");
   }
}
