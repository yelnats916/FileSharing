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
      AWSCredentials credentials = new BasicAWSCredentials(
         "AKIAJK6CDWQ7EEPXP45Q", "D2auJzePUKq2AHXbsU1W3ZFtiBRcMNupKEcjfIh6");

      // create a client connection based on credentials
      AmazonS3 conn = new AmazonS3Client(credentials);

      GeneratePresignedUrlRequest urlReq = null;
      String msgOutput = null;
      for (Bucket bucket : conn.listBuckets()) {
         msgOutput = bucket.getName();
         urlReq = new GeneratePresignedUrlRequest(bucket.getName(), "stang05HolidaySchedule.pdf");
      }
      /*
      s3client.putObject(new PutObjectRequest(bucketName, fileName, 
         new File("C:\\Users\\user\\Desktop\\testvideo.mp4")));
      */


      try {
         request.setAttribute("message", msgOutput);
         response.sendRedirect("" + conn.generatePresignedUrl(urlReq));
         //request.getRequestDispatcher("" + conn.generatePresignedUrl(urlReq)).forward(request,response);
         //request.getRequestDispatcher("/home.jsp").forward(request, response);
      } catch (Exception ex) {
         throw new ServletException(ex);
      }
   }
}

