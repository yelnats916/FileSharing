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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.common.io.ByteStreams;

public class DownloadServlet extends HttpServlet { 
         
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException, ServletException {
      AmazonS3 conn = new AmazonS3Client(new ProfileCredentialsProvider());
    
      GeneratePresignedUrlRequest urlReq = null;
      GetObjectRequest req = null;
      String user = request.getParameter("user");

      String bucketName = user + "-yelnats916";
      String msgOutput = bucketName;

      req = new GetObjectRequest(bucketName, "stang05HolidaySchedule.pdf");

      response.setContentType("application/pdf");
      // forces download
      String headerKey = "Content-Disposition";
      String headerValue = String.format("attachment; filename=\"%s\"", "stang05HolidaySchedule.pdf");
      response.setHeader(headerKey, headerValue);

      S3Object object = conn.getObject(req);
      InputStream inputStream = object.getObjectContent();
      OutputStream outputStream = response.getOutputStream();
      ByteStreams.copy(inputStream, outputStream);           
 
      inputStream.close();
      outputStream.flush();

      msgOutput = "" + conn.generatePresignedUrl(urlReq);
      try {
         request.setAttribute("message", msgOutput);
      //   response.sendRedirect("home.jsp");
         //request.getRequestDispatcher("" + conn.generatePresignedUrl(urlReq)).forward(request,response);
         request.getRequestDispatcher("/listing?" + "user=" + user).forward(request, response);
      } catch (Exception ex) {
         throw new ServletException(ex);
      }
   }
}

