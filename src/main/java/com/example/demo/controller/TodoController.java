package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	//****************@AuthenticationPrincipal
	//                = UsernamePasswordAuthenticationToken의 생성자의 첫매개변수
	//                즉,String userId
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		try {
			String temporaryUserId="temporary-user";//temporary user id
			//1.TodoEntity 변환 데이터베이스에 저장하기위해
			TodoEntity entity=TodoDTO.toEntity(dto);
			
			//2.id null로 초기화 생성 당시에는 id 없어야함
			entity.setId(null);
			
			//3.@AuthenticationPrincipal 설정 로그인 기능 나중에 부여가능한 부분
			entity.setUserId(userId);
			
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
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		String temporaryUserId="temporary-user";
		
		//1.서비스 메서드의 retrieve()메서드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(userId);
		
		//2.자바 스트림을 이용해 리턴된 리스트를 TodoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//3.변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
		ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		//4.ResponseDTO리턴
		return ResponseEntity.ok().body(response);
		
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		String temporaryUserId="temporary-user";//temporary user id
		
		//1.dto를 entity로 변환
		TodoEntity entity=TodoDTO.toEntity(dto); //TodoDTO인 이유(dto메서드가 아니라): toEntity가 static함수이므로 
		
		//2.userid를 temporaryUserId로 초기화 인증부분에서 수정필요
		entity.setUserId(userId);
		
		//3.서비스를 이용해 entity 업데이트
		List<TodoEntity> entities=service.update(entity);
		
		//4.자바스트림을 이용해 리턴된 엔티티리스트를 TodoDTO리스트로 변환
		List<TodoDTO> dtos= entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		//5.변환된 TodoDTO리스트를 RespondDTO에 초기화
		ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
	
		//6.ResponseEntity에 RespondDTO를 넣어 리턴
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto){
		try {
			String temporaryUserId="temporary-user";//temporary user id
			
			//1.TodoEntity로 변환한다.
			TodoEntity entity=TodoDTO.toEntity(dto);
			
			//2.userId를 entity에 설정
			entity.setUserId(userId);
			
			//3.서비스를 이용해 entity삭제
			List<TodoEntity> entities=service.delete(entity);
			
			//4.자바스트림 이용해 리턴된 엔티티리스트를 TodoDTO로 변환
			List<TodoDTO> dtos=entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			//5.변환된 TodoDTO리스트를 RespondDTO로 초기화
			ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			//6.ResponseEntity에 RespondDTO리턴
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			//7.예외가 있는 경우 dto대신 error메시지에 넣어 리턴
			String error=e.getMessage();
			ResponseDTO<TodoDTO> response=ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
	
}
