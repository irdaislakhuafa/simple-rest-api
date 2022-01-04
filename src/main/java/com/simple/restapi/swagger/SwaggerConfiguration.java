package com.simple.restapi.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
        // access Json API documentation
        // http://localhost:8080/v2-api-docs

        // using Swagger UI
        // http://localhost:8080/swagger-ui/#/
        @Bean
        public Docket apiDocs() {
                return new Docket(DocumentationType.SWAGGER_2)
                                .select()
                                .apis(RequestHandlerSelectors.basePackage("com.simple.restapi.controller"))
                                .paths(PathSelectors.any())
                                .build()
                                .apiInfo(new ApiInfo(
                                                "My Simple Rest API Documentation",
                                                "This is description of Simple Rest API",
                                                "v0.1",
                                                "https://term_of_service.com",
                                                new Contact(
                                                                "Irda Islakhu Afa", "https://my.domain.com",
                                                                "irdhaislakhuafa@gmail.com"),
                                                "Apache License",
                                                "https://www.apache.org/licenses/LICENSE-2.0",
                                                Collections.emptyList()));
        }
}
