package com.shafi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.Collections;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	private static final String ERROR = "Error";
	private static final int TWO_HUNDERED_ONE = 201;
	private static final int FOUR_HUNDRED = 400;
	private static final int FOUR_HUNDERED_ONE = 401;
	private static final int FOUR_HUNDERED_THREE = 403;
	private static final int FOUR_HUNDERED_FOUR = 404;
	private static final int FOUR_HUNDERED_NINE = 409;
	private static final int FIVE_HUNDERED = 500;
	
    @Bean
    public Docket createApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shafi.controller"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false);
//				.globalResponseMessage(RequestMethod.POST, postResponseMessage())
//				.globalResponseMessage(RequestMethod.GET, getResponseMessage());
                //.build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Retreive user info")
				.description("This is a swagger documentation for this API")
				.termsOfServiceUrl("www.aaislamiccenter.com")
				.contact(new Contact("team name", " ", "team email adress"))
				.version("V1")
				.build();
	}
    
    private List<ResponseMessage> postResponseMessage(){
		List<ResponseMessage> responseMessage = new ArrayList<>();
		responseMessage.add(new ResponseMessageBuilder().code(FOUR_HUNDRED).message("Input Validation Error").responseModel(new ModelRef(ERROR)).build());
		return responseMessage;
	}
    
	private List<ResponseMessage> getResponseMessage(){
		List<ResponseMessage> responseMessage = new ArrayList<>();
		
		return responseMessage;
	}
}