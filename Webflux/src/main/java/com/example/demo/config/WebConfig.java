//package com.example.demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//	public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        		.allowedOrigins("*")
//        		.allowedMethods("*")	
//                .allowedHeaders("*")
//                .exposedHeaders("*")
//                .allowCredentials(false).maxAge(3600)
//        .allowedMethods(ALLOWED_METHOD_NAMES.split(","));
////                .allowedMethods(HttpMethod.GET.name());
//    }
//}s