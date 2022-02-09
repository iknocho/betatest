package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints= {@UniqueConstraint(columnNames="email")})
public class UserEntity {
	@Id
	@GeneratedValue(generator="system-uuid")////ID를자동 생성, ID생성 방식 지정가능 system-uuid제너레이터를 사용하여 id생성 기본 제너레이터 INCREMENTAL,SEQUENCE,IDENTITY
	@GenericGenerator(name="system-uuid",strategy="uuid")//Hibernate가 제공하는 기본 제너레이터가 아닌 나만의 제너레이터 사용하고 싶을 때 사용 
	private String id;//사용자에게 고유부여하는 ID
	
	@Column(nullable=false)
	private String username;//사용자이름
	
	@Column(nullable=false)
	private String email; //사용자의 이메일 아이디와 같은 기능

	@Column(nullable=false)
	private String password;
}

