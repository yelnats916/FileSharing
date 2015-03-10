package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.common.io.*;

@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                  maxFileSize=1024*1024*50,          // 50 MB
                  maxRequestSize=1024*1024*100)      // 100 MB
public class UploadServlet extends HttpServlet {

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {
      Part filePart = null;
      for (Part part: request.getParts()) {
         filePart = part;
      }
      String fileName = getFileName(filePart);
      InputStream fileContent = filePart.getInputStream();
      
      byte[] fileBytes = ByteStreams.toByteArray(fileContent);
      Long contentLength = Long.valueOf(fileBytes.length);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(contentLength);
      
      AmazonS3 conn = new AmazonS3Client(new ProfileCredentialsProvider());

      AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

      String bucketName = "stang05bucket";
      try {
         s3client.putObject(new PutObjectRequest(bucketName, fileName, filePart.getInputStream(), metadata));
         request.setAttribute("message", "" + fileName + " " + "successfully uploaded");
         request.getRequestDispatcher("/home.jsp").forward(request, response);
      } catch (Exception ex) {
         throw ex;
      }
   }

   private String getFileName(Part part)
   {
      String contentDisposition=part.getHeader("content-disposition");
      for (  String token : contentDisposition.split(";")) {
         if (token.trim().startsWith("filename")) {
            return token.substring(token.indexOf('=') + 1).trim().replace("\"","");
         }
      }
      return null;
   }
}
