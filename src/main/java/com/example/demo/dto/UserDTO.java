package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder  //오브젝트 생성을 위한 디자인 패턴 생성자를 이용해 오브젝트를 생성하는 것 비슷함 빌더 패턴을 사용해 오브젝트 생성가능하다
@NoArgsConstructor //매개변수 없는 생성자를 구현한 것과 같다
@AllArgsConstructor	//클래스의 모든 멤버 변수를 매개변수로 받는 생성자 구현 
@Data //클래스 멤버변수의 모든 getter와 Setter를 구현해준다 
public class UserDTO {
	private String token;
	private String email;
	private String username;
	private String password;//보안에??DTO에?확인을 위해서 필요한
	private String id;
	
}
