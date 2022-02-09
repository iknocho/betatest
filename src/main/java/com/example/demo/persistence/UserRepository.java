package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserEntity;
//데이터베이스와 통신하며 필요한 쿼리를 보내고 해석해 엔티티 오브젝트로 변환
@Repository//JpaRepository<T,ID> T=테이블에 매핑될 엔티티 클래스, ID=기본 키의 타입 
public interface UserRepository extends JpaRepository<UserEntity,String> {
	UserEntity findByEmail(String email);
	Boolean existsByEmail(String email);
	UserEntity findByEmailAndPassword(String email,String password);
}
