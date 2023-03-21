package com.example.demo.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;//이거 안달면 꼭 기본 로그인창 가더라 ㅜㅜ
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   
   @Bean
   public PasswordEncoder getPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }
   
// 인증 제외할 정적 파일 리스트
   private static final String[] AUTH_WHITELIST = {
           "/swagger/**",
           "/swagger-ui/**",
           "/h2/**",
           "/api-docs/**"
   };
   
   /**
    * 정적 파일은 Securit	y 적용 제외
    */
   @Override
   public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers(AUTH_WHITELIST);
   }
   
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration configuration = new CorsConfiguration();
       configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
       configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
       configuration.addAllowedHeader("*");
       configuration.setAllowCredentials(true);
       configuration.setMaxAge(3600L);
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", configuration);
       return source;
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.cors().configurationSource(corsConfigurationSource())
               .and()
                   .csrf().disable()
                   .formLogin().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션대신 JWT 사용
               // .and()
               //     .authorizeRequests()
               //         // .antMatchers("/").permitAll()
               //         .antMatchers("/**/auths/**").permitAll()
               //         .antMatchers(HttpMethod.GET, "**/roles").permitAll()
               //         .antMatchers(HttpMethod.GET, "**/logs").permitAll()
               //         .anyRequest().authenticated()
               //         // .anyRequest().permitAll()
       ;
   }
   
   
   

//   protected void configure(HttpSecurity http) throws Exception {
//	      http.cors().disable()
//	         .csrf().disable()
//	         .formLogin().disable()
//	         .headers().frameOptions().disable();
//	   }
//   
//   @Autowired
//	javax.sql.DataSource dataSource;
	
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
//   @Autowired
//	public void configAuthentication(AuthenticationManagerBuilder auth) 
//		throws Exception {
//		
//		auth.jdbcAuthentication().dataSource(dataSource)
//			.usersByUsernameQuery("select id, pw, enabled from member where id = ?")
//			.authoritiesByUsernameQuery("select id, authority as role from authorities where username = ?")
//			.passwordEncoder(new BCryptPasswordEncoder());  /* 패스워드 암호화 시 사용 */
//	}	
}