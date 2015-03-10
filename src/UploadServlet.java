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

public class UploadServlet extends HttpServlet {

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
   throws IOException, ServletException {
      Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
      String fileName = getFileName(filePart);
      InputStream fileContent = filePart.getInputStream();
      
      AmazonS3 conn = new AmazonS3Client(new ProfileCredentialsProvider());      
   }

   private String getFileName(Part filePart)
   {
      String header = filePart.getHeader("content-disposition");
      if(header == null)
         return null;
      for(String headerPart : header.split(";"))
      {
        if(headerPart.trim().startsWith("filename"))
         {
            return headerPart.substring(headerPart.indexOf('=') + 1).trim()
                   .replace("\"", "");
         }
      }
      return null;
   }

}
