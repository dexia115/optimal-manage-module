package com.optimal.kdm.module.manage.starter;

import java.util.Collections;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Slf4j
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnClass({ ApiInfo.class })
public class SwaggerAutoconfiguration {

	@Bean
	public Docket swaggerSpringfoxApiDocket() {
		log.info("Starting Swagger");
		Contact contact = new Contact("Optimal", "www.koudaimiao.com", "dexia115@sina.com");

		ApiInfo apiInfo = new ApiInfo("Optimal Manage Application API", "API documentation", "1.0.0", "", contact, "",
				"", Collections.emptyList());

		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(apiInfo).forCodeGeneration(true).directModelSubstitute(java.nio.ByteBuffer.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
		docket.host(String.format("%s:%d", "localhost", 8090));
		docket = docket.select().paths(regex("/.*")).apis(RequestHandlerSelectors.basePackage("com.optimal.kdm"))
				.build();
		log.info("==>.Started Swagger in {} ms, config properties : {}");
		return docket;
	}

}
