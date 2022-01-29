package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;


@RestController
@RequestMapping("test")
public class TestController{
	/*
	 * @GetMapping() public String testController() { return "Hello World!"; }
	 */	 
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "Hello World! testgetMapping";
	}
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required=false) int id) {
		return "hello world! ID"+id;
	}
	@GetMapping("testRequestParam")
	public String testControllerRequestParam(@RequestParam(required=false) int id) {
		return "Hello World! ID"+id;
	}
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "Hello World! "+testRequestBodyDTO.getId()+" 1Message: "+testRequestBodyDTO.getMessage();
	}
	
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
	}
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list=new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response=ResponseDTO.<String>builder().data(list).build();
		//ResponEntity와 내가 만든 dto와의 차이 헤더와 Http Status 조작가능 차이를 보인
		return ResponseEntity.badRequest().body(response);
	}
	
	@GetMapping("/testResponseEntity2")
	public ResponseEntity<?> testControllerResponseEntity2(){
		List<String> list=new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 200!");
		ResponseDTO<String> response=ResponseDTO.<String>builder().data(list).build();
		//ResponEntity와 내가 만든 dto와의 차이 헤더와 Http Status 조작가능 차이를 보인
		return ResponseEntity.ok().body(response);
	}
	
	
}
