package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
	private String error;
	private List<T> data;
	
//	public ResponseDTO(final ResponseEntity<T> entity) {
//		this.error=entity.getError();
//		this.data=entity.getData();
//		
//	}
}
