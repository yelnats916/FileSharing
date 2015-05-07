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
      String fileKey = request.getParameter("fileKey");
      GeneratePresignedUrlRequest urlReq = new GeneratePresignedUrlRequest(bucketName, fileKey);

      //response.setContentType("text/plain");
      // forces download
      String headerKey = "Content-Disposition";
      String headerValue = String.format("inline; filename=\"%s\"", fileKey);
      response.setHeader(headerKey, headerValue); 

      try {
         response.sendRedirect("" + conn.generatePresignedUrl(urlReq));
      } catch (Exception ex) {
         throw new ServletException(ex);
      }
   }
}

