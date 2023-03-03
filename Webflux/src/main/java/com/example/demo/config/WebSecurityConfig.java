package com.example.demo.config;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;//이거 안달면 꼭 기본 로그인창 가더라 ㅜㅜ
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   @Bean
   public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }
   

   protected void configure(HttpSecurity http) throws Exception {
	      http.cors().disable()
	         .csrf().disable()
	         .formLogin().disable()
	         .headers().frameOptions().disable();
	   }
   
   @Autowired
	javax.sql.DataSource dataSource;
	
//	@Override
//   protected void configure(HttpSecurity http) throws Exception {
//       http.authorizeRequests()
//               .antMatchers("/resource/**", "/login", "/login-error").permitAll() /*인증이 필요없는 정적 데이터*/
//               .antMatchers("/main").hasRole("USER") //.antMatchers("/main").access("hasRole('ROLE_ADMIN')") 와 같음
//               .anyRequest().authenticated() /* 그 외 모든 요청은 인증된 사용자만 접근이 가능하게 처리*/
//
//       .and().formLogin()
//               .failureUrl("/login?error") /*로그인 실패시 url*/
//               .defaultSuccessUrl("/main", true) /*로그인 성공시 url*/
//               .permitAll()
//       .and().logout()
//               .logoutUrl("/logout")  /*로그아웃 url*/
//               .logoutSuccessUrl("/login")  /*로그아웃 성공시 연결할 url*/
//               .permitAll();
//       http.csrf().disable();
//   }
//    
   @Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) 
		throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select id, pw, enabled from member where id = ?")
			.authoritiesByUsernameQuery("select id, authority as role from authorities where username = ?")
			.passwordEncoder(new BCryptPasswordEncoder());  /* 패스워드 암호화 시 사용 */
	}	
}