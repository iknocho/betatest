package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	@Autowired//자동으로 오브젝트 찾아 연결 new생성 
	private TodoRepository repository;
	public String testService() {
		//TodoEntity생성
		TodoEntity entity=TodoEntity.builder().title("My first todo item").build();
		//TodoEntity저장
		repository.save(entity);
		//TodoEntity 검색
		TodoEntity savedEntity=repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	////////////////////////CREATE
	
	public List<TodoEntity> create(final TodoEntity entity){//final TodoEntity 인자값 =entity의 참조값을 바꾸지 못한
	//validation
	validate(entity);
	//save
	repository.save(entity);
	log.info("Entity Id: {} is saved",entity.getId());
	
	return repository.findByUserId(entity.getUserId());
	}
	/////////////////////////RETRIEVE
	
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	///////////////////////UPDATE
	
	public List<TodoEntity> update(final TodoEntity entity){
		//1.저장할 엔티티의 유효성
		validate(entity);
		
		//2.넘겨받은 엔티티id를 이용해 TodoEntity를 가져온다 
		Optional<TodoEntity> original
		
	}
	
	/////////////////////////
	private void validate(final TodoEntity entity) {
		if(entity==null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");//runtimeException실행시 발생하는 예외 예측불가능한 예외, Exception예측가능한 예외
		}
		
		if(entity.getUserId()==null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
}
