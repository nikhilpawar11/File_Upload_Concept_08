package com.nikhil.learn.service;

import java.io.IOException;
import java.util.List;

import com.nikhil.learn.dto.UserDto;
import com.nikhil.learn.pegination.PegebleResponse;

public interface UserService {
	
	// to save or create user
	public UserDto saveUser(UserDto userDto);
	
	//to update user
	public UserDto updateUSer(UserDto userDto, String userId);
	
	//to delete user
	public void deleteUser(String userId) throws IOException;
	
	//to get single user
	public UserDto getUser(String userId);
	
	//to get all user
	public List<UserDto> getAllUser();
	
	//to get user by name
	public UserDto getUserByName(String name);
	
	//to get all user with pegination
	public PegebleResponse getAllUsersWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir);

}
