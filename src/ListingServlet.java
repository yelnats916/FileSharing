package servlets;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.TokenParser;
import com.google.common.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class ListingServlet extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {

      try {
      AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

      String bucketName =  request.getParameter("user").toLowerCase() + "-yelnats916";
      if (!(s3client.doesBucketExist(bucketName))) {
         CreateBucketRequest createReq = new CreateBucketRequest(bucketName);
         createReq.setCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
         s3client.createBucket(createReq);
      }

      ArrayList<String> fileKeys = new ArrayList<String>();
      ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName);
      ObjectListing objectListing;            
      do {
         objectListing = s3client.listObjects(listObjectsRequest);
         for (S3ObjectSummary objectSummary :  objectListing.getObjectSummaries()) {
            fileKeys.add(objectSummary.getKey());
         }
         listObjectsRequest.setMarker(objectListing.getNextMarker());
      } while (objectListing.isTruncated());
      
      // get friends' keys
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fileshare", "root", "stanley");
      PreparedStatement stmt =
         con.prepareStatement("SELECT friend from Friends where user=?");
      stmt.setString(1, request.getParameter("user"));
      ResultSet rs = stmt.executeQuery();

      String currFriend = null;
      String friendBucketName = null;
      ArrayList<String> friendFileKeys = new ArrayList<String>();
      while (rs.next()) {
         currFriend = rs.getString("friend");
         friendBucketName = currFriend.toLowerCase() + "-yelnats916";
         if ((s3client.doesBucketExist(friendBucketName))) {
            listObjectsRequest = new ListObjectsRequest().withBucketName(friendBucketName);
            do {
               objectListing = s3client.listObjects(listObjectsRequest);
               for (S3ObjectSummary objectSummary :  objectListing.getObjectSummaries()) {
                  friendFileKeys.add(currFriend.toLowerCase() + "=" + objectSummary.getKey());
               }
               listObjectsRequest.setMarker(objectListing.getNextMarker());
            } while (objectListing.isTruncated());
         }
      }
      stmt.close();
      con.close();
      
      request.setAttribute("fileKeys", fileKeys);
      request.setAttribute("friendFileKeys", friendFileKeys);
      request.getRequestDispatcher("/home.jsp").forward(request, response);
      return;
      } catch (Exception ex) {
         throw new ServletException(ex);
      }
   }
}
