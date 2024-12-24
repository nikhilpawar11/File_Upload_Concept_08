package com.nikhil.learn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.learn.dto.UserDto;
import com.nikhil.learn.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	// make custom finder method : findByName
	
	Optional<User> findByName(String name);

}
