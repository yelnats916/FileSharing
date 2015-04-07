package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Servlet extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {
      AmazonS3 conn = new AmazonS3Client(new ProfileCredentialsProvider());

      String bucketName = request.getParameter("user") + "-yelnats916";
      GeneratePresignedUrlRequest urlReq = new GeneratePresignedUrlRequest(bucketName, "RARBG.com.mp4");

      //response.setContentType("application/pdf");
      // forces download
      //String headerKey = "Content-Disposition";
      //String headerValue = String.format("attachment; filename=\"%s\"", "stang05HolidaySchedule.pdf");
      //response.setHeader(headerKey, headerValue); 

      try {
         response.sendRedirect("" + conn.generatePresignedUrl(urlReq));
         //request.getRequestDispatcher("" + conn.generatePresignedUrl(urlReq)).forward(request,response);
         //request.getRequestDispatcher("/home.jsp").forward(request, response);
      } catch (Exception ex) {
         throw new ServletException(ex);
      }
   }
}

