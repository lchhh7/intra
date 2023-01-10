package com.jinjin.jintranet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring().antMatchers(
				"common/**"
        );
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin();
		
		http.cors().disable().csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/login.do" , "/auth/**" , "/common/**" , "/join.do")
		.permitAll()
		.antMatchers("/admin/**")
		.hasRole("ADMIN")
		.anyRequest().authenticated()
		
		.and().formLogin().loginPage("/login.do")
		.loginProcessingUrl("/auth/loginProc")
		.defaultSuccessUrl("/main.do");
		
		return http.build();
	}
}
