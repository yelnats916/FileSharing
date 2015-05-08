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
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.TokenParser;
import javax.activation.MimetypesFileTypeMap;
import javax.activation.FileTypeMap;
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
         if (part.getName().equals("fileName")) {
            filePart = part;
         }
      }
      String fileName = getFileName(filePart);
      
      MimetypesFileTypeMap mime = buildMimeMap();
      String mimeType = mime.getContentType(fileName);
      
      InputStream fileContent = filePart.getInputStream();
     
      byte[] fileBytes = ByteStreams.toByteArray(fileContent);
      Long contentLength = Long.valueOf(fileBytes.length);
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(contentLength);
      metadata.setContentType(mimeType);

      AmazonS3 conn = new AmazonS3Client(new ProfileCredentialsProvider());

      AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

      String user = request.getParameter("user").toLowerCase();
      String bucketName = user + "-yelnats916";
      
      try {
         s3client.putObject(bucketName, fileName, filePart.getInputStream(), metadata);
         response.sendRedirect("listing?" + "user=" + user);
         return;
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

   private MimetypesFileTypeMap buildMimeMap() {
      MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
      fileTypeMap.addMimeTypes("audio/mpeg mp3");
      fileTypeMap.addMimeTypes("image/bmp bmp");
      fileTypeMap.addMimeTypes("image/gif gif");
      fileTypeMap.addMimeTypes("image/jpeg jpg jpeg jpe");
      fileTypeMap.addMimeTypes("image/png png");
      fileTypeMap.addMimeTypes("text/css css");
      fileTypeMap.addMimeTypes("text/csv csv");
      fileTypeMap.addMimeTypes("text/html htm html");
      fileTypeMap.addMimeTypes("text/xml xml");
      fileTypeMap.addMimeTypes("text/plain txt");
      fileTypeMap.addMimeTypes("video/mp4 mp4");
      fileTypeMap.addMimeTypes("application/msword docx doc");
      fileTypeMap.addMimeTypes("application/json json");
      fileTypeMap.addMimeTypes("application/pdf pdf");
      return fileTypeMap;
   }
}
