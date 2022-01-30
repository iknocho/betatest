package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
		Optional<TodoEntity> original = repository.findById(entity.getId());
		
		//3.반환된 TodoEntity가 존재하면 새 entity값으로 덮어 씌운다.
		original.ifPresent(todo->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
			//4.데이터베이스에 새 값을 저장한다.
			repository.save(todo);
		});
		//retrieve Todo에서 만든 메서드를 이용해 사용자의 모든 Todo리스트를 반환 굳이 모든 투드리스트?
		return retrieve(entity.getUserId());
	}
	
	/////////////////////////DELETE
	public List<TodoEntity> delete(final TodoEntity entity){
		//1.저장할 엔티티 유효
		validate(entity);
		
		//2.엔티티를 삭제한다
		try {
			repository.delete(entity);
		} catch(Exception e){//Exception과 RuntimeException정확한차이??
			//3.예외발생시 id와 exception을 로깅
			log.error("error deleting entity",entity.getId(),e);
			
			//4.컨트롤러로 exception을 보낸다. 데이터베이스 내부 로직을 캡슐하려면 e를 리턴하지않고 새 exception 오브젝트를 리턴한다
			throw new RuntimeException("error deleting entity"+entity.getId());
		}
		//5. 새 todo리스트를 가져와 리턴한다
		return retrieve(entity.getUserId());
		
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
