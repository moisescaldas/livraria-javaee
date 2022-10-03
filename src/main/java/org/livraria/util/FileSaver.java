package org.livraria.util;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.livraria.util.factory.FileFactory;

@RequestScoped
public class FileSaver {

	private static final String CONTENT_DISPOSITION = "content-disposition";
	private static final String FILENAME_KEY = "filename=";
	private static final String BUCKET_NAME = "livraria";

	private AmazonS3 s3;
	
	public FileSaver() {
	}

	@Inject
	public FileSaver(AmazonS3 s3) {
		this.s3 = s3;
	}

	/*
	 * public String write(String baseFolder, Part multipartFile) { 
	 * String serverPath = request.getServletContext().getRealPath("/" + baseFolder);
	 * String fileName =
	 * extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION)); 
	 * String path = serverPath + "/" + fileName;
	 * 
	 * try { 
	 * multipartFile.write(path);
	 * 
	 * } catch (IOException e) { throw new RuntimeException(e); }
	 * 
	 * return baseFolder + "/" + fileName; }
	 */
	
	public String write(String baseFolder, Part multipartFile) {
		String fileName = extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION));
		//String serverPath = request.getServletContext().getRealPath("/" + baseFolder);
		//String path = serverPath + "/" + fileName;
		
		try {
			File file = FileFactory.getTempFile(multipartFile.getInputStream());
			
			PutObjectRequest objectRequest = new PutObjectRequest(BUCKET_NAME, fileName, file);
			
			s3.putObject(objectRequest);
			
			return s3.getUrl(BUCKET_NAME, fileName).toString();
			
		} catch (AmazonS3Exception | IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private String extractFilename(String content) {
		if (content == null) {
			return null;
		}
		int startIndex = content.indexOf(FILENAME_KEY);
		if (startIndex == -1) {
			return null;
		}
		String filename = content.substring(startIndex + FILENAME_KEY.length());
		if (filename.startsWith("\"")) {
			int endIndex = filename.indexOf("\"", 1);
			if (endIndex != -1) {
				return filename.substring(1, endIndex);
			}
		} else {
			int endIndex = filename.indexOf(";");
			if (endIndex != -1) {
				return filename.substring(0, endIndex);
			}
		}
		return filename;
	}
}
