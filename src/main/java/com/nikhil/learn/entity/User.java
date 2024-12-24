package com.nikhil.learn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class User {
	
	@Column(name = "User Id")
	@Id
	private String id;
	
	@Column(name = "User Name")
	private String name;
	
	@Column(name= "UserImage")
	private String imageName;
	
	

}
