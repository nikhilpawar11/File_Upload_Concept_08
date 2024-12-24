package com.nikhil.learn.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nikhil.learn.dto.UserDto;
import com.nikhil.learn.exception.ApiResponseMessage;
import com.nikhil.learn.exception.ImageResponse;
import com.nikhil.learn.pegination.PegebleResponse;
import com.nikhil.learn.service.FileService;
import com.nikhil.learn.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	
	
	@PostMapping("/saveUser")
	public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
		
		UserDto savedUser = userService.saveUser(userDto);
		
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId){
		
		UserDto updatedUser = userService.updateUSer(userDto, userId);
		
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) throws IOException{
		
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("User deleted successfully !!").success(true).status(HttpStatus.OK).build();
		
		userService.deleteUser(userId);
		
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
		
	}
	
	@GetMapping("/userById/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId){
		
		UserDto getUserById = userService.getUser(userId);
		
		return new ResponseEntity<>(getUserById, HttpStatus.OK);
		
	}
	
	@GetMapping("/allUser")
	public ResponseEntity<List<UserDto>> getAllUser(){
		
		List<UserDto> allUser = userService.getAllUser();
		
		return new ResponseEntity<>(allUser, HttpStatus.OK);
		
	}
	
	@GetMapping("/userByName/{name}")
	public ResponseEntity<UserDto> getUserByName(@PathVariable String name){
		
		UserDto userByName = userService.getUserByName(name);
		
		return new ResponseEntity<>(userByName, HttpStatus.OK);
		
	}
	
	@GetMapping("/allUsersWithPegination")
	public ResponseEntity<PegebleResponse> getallUserWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegebleResponse allUsersWithPegination = userService.getAllUsersWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(allUsersWithPegination, HttpStatus.OK);
		
	}
	
	// make a API to upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException{
		
		String imageName = fileService.uploadFile(image, imageUploadPath);
		
		UserDto user = userService.getUser(userId);
		
		user.setImageName(imageName);
		
		UserDto updateUSer = userService.updateUSer(user, userId);
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image upload successfull !!").success(true).status(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
		
	}
	
	
	// make a API to serve image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		
		UserDto userById = userService.getUser(userId);
		
		InputStream resource = fileService.getResource(imageUploadPath, userById.getImageName());
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
		
	}

}
