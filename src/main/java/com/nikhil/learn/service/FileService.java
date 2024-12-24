package com.nikhil.learn.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	// make method to save file
	public String uploadFile(MultipartFile file, String path) throws IOException;
	
	// make a method to serve the resource 
	public InputStream getResource(String path, String name) throws IOException;

}
