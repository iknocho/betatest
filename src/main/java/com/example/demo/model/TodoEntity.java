package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder  //오브젝트 생성을 위한 디자인 패턴 생성자를 이용해 오브젝트를 생성하는 것 비슷함 빌더 패턴을 사용해 오브젝트 생성가능하다
@NoArgsConstructor //매개변수 없는 생성자를 구현한 것과 같다
@AllArgsConstructor	//클래스의 모든 멤버 변수를 매개변수로 받는 생성자 구현 
@Data //클래스 멤버변수의 모든 getter와 Setter를 구현해준다 
@Entity//자바클래스를 엔티티로 지정 매개변수를 넣어 이름부여
@Table(name="Todo")//데이터베이스 Todo테이블에 매핑 name설정 없을시 entity의 이름 또는 class이름으로 자동 매핑된다.
public class TodoEntity {
	@Id//프라이머리키 설정
	@GeneratedValue(generator="system-uuid")//ID를자동 생성, ID생성 방식 지정가능 system-uuid제너레이터를 사용하여 id생성 기본 제너레이터 INCREMENTAL,SEQUENCE,IDENTITY
	@GenericGenerator(name="system-uuid",strategy="uuid")//Hibernate가 제공하는 기본 제너레이터가 아닌 나만의 제너레이터 사용하고 싶을 때 사용 
	private String id;
	private String userId;
	private String title;
	private boolean done;
	
	
}
