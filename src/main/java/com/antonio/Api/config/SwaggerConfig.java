package com.antonio.Api.config;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.*;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;


import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket createRestApi() {
		return new Docket (DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.antonio.Api")).
				build()
				.apiInfo(apiInfo())
				.select()
				.paths(pathRegex())
				.build();
	}
	
	private Predicate <String> pathRegex(){
		return or(regex ("/bank.*"));
		
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Projeto Api",
				"Projeto para finalização da Trilha Java - Share RH",
				"1.0.0",
				"http://apache.org",
				new Contact ("Antonio Henrique Passos Santos", "http://projetoApi.api.com","ahpsantos@gmail.com"),
				"Licenca API",
				"http://www.apache.org/license.html",
				new ArrayList<VendorExtension>());
				
	}

}
