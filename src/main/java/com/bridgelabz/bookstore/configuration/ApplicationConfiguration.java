package com.bridgelabz.bookstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class ApplicationConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bridgelabz.bookstore"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Book Store API",
                "Book Store is An online bookstore project containing various books in stock along with their name, author and cost."+
                        "This project is developed using java for Back-end and React for Front-end. "+
                        "The user may select desired book ."+
                        "User may even search for specific books ." +
                        "Once the user add book to cart, then user fill details so user can purchased book ."+
                        "After that user get email of Successful placed order with order-id  ",
                "1.0",
                "http://www.bridgelabz.com",
                new Contact("Bridgelabz", "http://www.bridgelabz.com", "contactus@bridgelabz.com"),
                "License of API", "http://www.bridgelabz.com", Collections.emptyList());
    }

}
