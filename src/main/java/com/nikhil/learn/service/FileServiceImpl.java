package com.nikhil.learn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nikhil.learn.exception.BadApiRequest;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFilename = file.getOriginalFilename();
		
		String filename = UUID.randomUUID().toString();
		
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		
		String fileNameWithExtension = filename + extension;
		
		String fullPathWithFileName = path + fileNameWithExtension;
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
			
			//file save
			File folder = new File(path);
			
			if(!folder.exists()) {
				
				//create new folder
				
				boolean mkdirs = folder.mkdirs();
				
			}
			
			// upload the file
			
			Files.copy(file.getInputStream(), Path.of(fullPathWithFileName));
			
			return fileNameWithExtension;
			
		} else {
			throw new BadApiRequest("File with this "+ extension + " not allowed !!");
		}
	
	}

	
	@Override
	public InputStream getResource(String path, String name) throws IOException {
		
		String fullPath = path + File.separator + name;
		
		InputStream inputStream = new FileInputStream(fullPath);
		
		return inputStream;
	}

}
