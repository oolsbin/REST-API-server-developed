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
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
   }

}