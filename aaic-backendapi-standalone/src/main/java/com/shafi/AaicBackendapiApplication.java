package com.shafi;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class AaicBackendapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AaicBackendapiApplication.class, args);
	}

	
	@PostConstruct
    public void init(){
      // Setting Spring Boot SetTimeZone
      TimeZone.setDefault(TimeZone.getTimeZone("CST"));
    }
	
//	@Configuration
//    public class WebConfig implements WebMvcConfigurer {
//
//        @Override
//        public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/").setViewName("forward:/index.html");
//        }
//    }
	
//	@Configuration
//	public class WebConfig implements WebMvcConfigurer {
//
//	    @Override
//	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	        registry.addResourceHandler("/videos/**")
//	                .addResourceLocations("classpath:/static/videos/");
//	    }
//	}
	
}
