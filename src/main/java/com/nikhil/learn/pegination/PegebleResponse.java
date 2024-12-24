package com.nikhil.learn.pegination;

import java.util.List;

import com.nikhil.learn.dto.UserDto;

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
public class PegebleResponse {
	
	private List<UserDto> content;
	
	private long totalElements;
	
	private int totalPages;
	
	private Boolean isFirst;
	
	private Boolean isLast;
	
	private int pageNumber;
	
	private int pageSize;
	

}
