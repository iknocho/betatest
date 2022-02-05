package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
		private final long Max_AGE_SECS=3600;
		
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			//모든 경로에 대해
			registry.addMapping("/**")
			//origin이 localhost3000에 대해
			.allowedOrigins("http://localhost:3000")
			//get,post,put,patch,delete/options메서드 허용
			.allowedMethods("GET","POST","PUT","PATCH","DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(Max_AGE_SECS);
		}
	
}
