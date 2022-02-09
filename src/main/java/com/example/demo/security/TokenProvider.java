package com.example.demo.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;

import java.security.Key;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

@Slf4j
@Service
public class TokenProvider {
	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
	String secretString = Encoders.BASE64.encode(key.getEncoded());
	public String create(UserEntity userEntity) {
		//기한은 현재부터 ~ 1일로 설정
		Date expiryDate=Date.from(
				Instant.now()
				.plus(1,ChronoUnit.DAYS));
		/*
		 * {//header "alg":"HS256" //토큰 서명을 할때 사용할 해쉬알고리즘 }.
		 * { //payload
		 * "sub":"2c9e81907ed26dad017ed26dcbfe0000",//토큰의 주인 ID와 같은 유일한 식별자 "iss":"beta app",//토큰
		 * 발행 주체 "iat":1644207928, //토큰 발행 날짜와 시간 "exp":1644294328//토큰 만료 시 }. //시크릿 키를
		 * 이용한 서명
		 */
	//JWT token생성
    return Jwts.builder()
            //header에 들어갈 내용 및 서명을 하기위한 SECRET_KEY
		.signWith(key)
            //payload에 들어갈 내용
        .setSubject(userEntity.getId())//sub
        .setIssuer("beta")//iss
        .setIssuedAt(new Date())//iat
        .setExpiration(expiryDate)//exp
        .compact();
	}

	public String validateAndGetUserId(String token){
	    //parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
		//위조되지 않았다면 페이로드(claims) 리턴, 위조라면 예외를 날림
		//그중 우리는 userId가 필요함 getbody를 부른다
		Claims claims=Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();

    }

}
