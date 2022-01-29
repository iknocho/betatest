package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {

	@Autowired
	private TodoService service;
	
	@GetMapping("/test")
	public ResponseEntity<?> testtodo(){
		String str=service.testService();//테스트 서비스 사
		List<String> list=new ArrayList<>();
		list.add(str);
		ResponseDTO<String> response=ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId="temporary-user";//temporary user id
			//1.TodoEntity 변환 데이터베이스에 저장하기위해
			TodoEntity entity=TodoDTO.toEntity(dto);
			
			//2.id null로 초기화 생성 당시에는 id 없어야함
			entity.setId(null);
			
			//3.임시 사용자 아이디를 설정 로그인 기능 나중에 부여가능한 부분
			entity.setUserId(temporaryUserId);
			
			//4.서비스를 이용해 TodoEntity생성
			List<TodoEntity> entities=service.create(entity);
			
			//5.자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다. 
			//stream????????????
			List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//6.변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다
			ResponseDTO<TodoDTO> response= ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//7.ResponseDTO리턴한다
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			String error=e.getMessage();
			ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId="temporary-user";
		
		//1.서비스 메서드의 retrieve()메서드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		
		//2.자바 스트림을 이용해 리턴된 리스트를 TodoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//3.변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		//4.ResponseDTO리턴
		return ResponseEntity.ok().body(response);
		
	}
	
	
}
