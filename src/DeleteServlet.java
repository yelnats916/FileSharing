package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class DeleteServlet extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {

      AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
      String user = request.getParameter("user").toLowerCase();
      String bucketName = user + "-yelnats916";
      String fileKey = request.getParameter("fileKey");     
 
      try {
         if (!fileKey.equals("")) {
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileKey));
         }
         response.sendRedirect("listing?" + "user=" + user);
         return;
      } catch (Exception ex) {
         throw ex;
      }
   }

}
