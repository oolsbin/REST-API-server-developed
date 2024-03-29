package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration	// 스프링 실행시 설정파일 읽어드리기 위한 어노테이션 
@EnableSwagger2	// Swagger2를 사용하겠다는 어노테이션 
@SuppressWarnings("unchecked")	// warning밑줄 제거를 위한 태그 
public class SwaggerConfig extends WebMvcConfigurationSupport {

	//리소스 핸들러 설
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfoBuilder()
	            .title("국내 항공 운항 정보")
	            .description("국내선 항공운항정보를 조회하는 국내항공운항정보 서비스 입니다.\n"
	            		+ "회원가입 및 로그인을 통해서 사용자 인증이 되면 국내 항공편을 예매할 수 있습니다.")
	            .version("2.x")
	            .build();
	}
	
	
	@Bean
	public Docket api() {
	   return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
	     .select()
	     .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	     .build();
	}
	


    // API마다 구분짓기 위한 설정.
//	@Bean
//    public Docket productApi() {
//        return getDocket("유저", Predicates.or(
//                PathSelectors.regex("/user.*")));
//    }
//
//    
//    @Bean
//    public Docket searchApi() {
//        return getDocket("예약", Predicates.or(
//                PathSelectors.regex("/reservation.*")));
//    }
//
//    
//    @Bean
//    public Docket commonApi() {
//        return getDocket("공통", Predicates.or(
//                PathSelectors.regex("/test.*")));
//        		
//    }
//
//    @Bean
//    public Docket allApi() {
//        return getDocket("전체", Predicates.or(
//                PathSelectors.regex("/*.*")));
//    }
	
    //swagger 설정.
    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(predicate)
                .apis(RequestHandlerSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getCustomizedResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getCustomizedResponseMessages());
    }
    
    //swagger ui 설정.
    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .build();
    }
    
    private List<ResponseMessage> getCustomizedResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("성공").build());
        responseMessages.add(new ResponseMessageBuilder().code(204).message("데이터 미존재").build());
        responseMessages.add(new ResponseMessageBuilder().code(400).message("입력값 오류").build());
        responseMessages.add(new ResponseMessageBuilder().code(401).message("미 로그인").build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("권한없음").build());
        responseMessages.add(new ResponseMessageBuilder().code(412).message("처리중 오류").build());
        return responseMessages;
    }
	
}