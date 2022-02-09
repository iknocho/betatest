package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http 시큐리티 빌더
        http.cors()//WebMvcConfig에서 이미 설정 했으므로 기본 cors설정
                .and()
                .csrf()//csrf는 현재 사용하지않으므로 dsiable
                    .disable()
                .httpBasic()//tokenㅇ을 사용하므로 basic인증 disable
                    .disable()
                .sessionManagement()//session기반 아님을 선언
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //*******인증해야하는API설정
                .authorizeRequests()//   /와 /auth/**경로는 인증안해도 된다
                    .antMatchers("/","/auth/**").permitAll()
                .anyRequest() //    /와 /auth/**이외의 모든 경로는 인증해야됨
                    .authenticated();
                //filter등록
                //매 요청마다
                //CorsFilter실행한 후에
                //jwtAuthenticationFilter실행한다
                http.addFilterAfter(
                        jwtAuthenticationFilter,
                        CorsFilter.class
                );
    }
}
