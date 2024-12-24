package com.nikhil.learn.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikhil.learn.dto.UserDto;
import com.nikhil.learn.entity.User;
import com.nikhil.learn.exception.ResourceNotFoundException;
import com.nikhil.learn.pegination.PegebleResponse;
import com.nikhil.learn.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	

	@Override
	public UserDto saveUser(UserDto userDto) {
		// convert dto into enity
		User user = mapper.map(userDto, User.class);
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		// save entity
		User saveUser = userRepo.save(user);
		// then convert entity into dto
		return mapper.map(saveUser, UserDto.class);
	}

	@Override
	public UserDto updateUSer(UserDto userDto, String userId) {
		//find user by id
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		//set user details
		userById.setName(userDto.getName());
		userById.setImageName(userDto.getImageName());
		//save user
		User updateUser = userRepo.save(userById);
		//convert entity into dto
		return mapper.map(updateUser, UserDto.class);
	}

	@Override
	public void deleteUser(String userId) throws IOException {
		
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		// delete user profile image
		String fullPath = imagePath + userById.getImageName();
		
		try {
			
			Path path = Paths.get(fullPath);
			Files.delete(path);
			
		} catch(NoSuchFileException ex) {
			ex.printStackTrace();
		}
		
		userRepo.delete(userById);
		
	}

	@Override
	public UserDto getUser(String userId) {
		
		User userById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id "+userId));
		
		return mapper.map(userById, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> allUser = userRepo.findAll();
		
		List<UserDto> userList = allUser.stream().map(ex -> mapper.map(ex, UserDto.class)).collect(Collectors.toList());
		
		return userList;
	}
	
	
	@Override
	public UserDto getUserByName(String name) {
		
		User userByName = userRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User not found with this name "+name));
		
		return mapper.map(userByName, UserDto.class);
	}
	

	@Override
	public PegebleResponse getAllUsersWithPegination(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<User> page = userRepo.findAll(pageable);
		
		List<User> content = page.getContent();
		
		List<UserDto> allUserList = content.stream().map(ex -> mapper.map(ex, UserDto.class)).collect(Collectors.toList());
		
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		boolean first = page.isFirst();
		boolean last = page.isLast();
		
		PegebleResponse pegebleResponse = PegebleResponse.builder().content(allUserList)
				.totalElements(totalElements).totalPages(totalPages)
				.isFirst(first).isLast(last).pageNumber(pageNumber)
				.pageSize(pageSize).build();
		
		return pegebleResponse;
	}

}
