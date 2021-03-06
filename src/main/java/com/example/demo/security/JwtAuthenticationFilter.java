package com.example.demo.security;


import com.example.demo.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;//crete, validate And Get UserId

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
        try {
            //요청에서 토큰가져오기
            String token=parseBearerToken(request);
            log.info("Filter is running...");
            //토큰 검사하기 JWT이므로 인가 서버에 요청하지 않고도 검증가능
            if(token!=null && !token.equalsIgnoreCase("null")){
                //userId가져오기. 위조된 경우 예외처리.
                String userId=tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID:"+userId);

                AbstractAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
                        userId,//인증된 사용자의 정보. 문자열이 아니어도 아무것이나 넣을 수 있다. 보통 UserDetails라는 오브젝트를 넣는다
                        null,//신용
                        AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                //인증완료 SecurityContextHolder에 등록해야 인증된 사용자라고 생각
                SecurityContextHolder.setContext(securityContext);
            }
        }catch(Exception ex){
            logger.error("Could not set user authentication in security context",ex);
            //ResponseDTO responseDTO= ResponseDTO.builder().error(ex.getMessage()).build();
            //return ResponseEntity.badRequest().body(responseDTO);  메세지로 보내느 방법이 있나??
        }
        filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request){
        //HTTP 요청의 헤더를 파싱해 Bearer 토큰을 리턴
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
