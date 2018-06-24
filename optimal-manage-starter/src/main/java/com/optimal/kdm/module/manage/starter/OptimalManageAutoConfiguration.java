package com.optimal.kdm.module.manage.starter;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.optimal.kdm.module.manage.biz.service.impl.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableSwagger2
@Profile({ "dev", "test" })
@ConditionalOnWebApplication
@EnableConfigurationProperties({ OptimalManageProperties.class, SwaggerProperties.class })
@ComponentScan(basePackages = { "com.optimal.kdm.module.manage" })
@EntityScan("com.optimal.kdm.module.manage.biz.entity")
public class OptimalManageAutoConfiguration {

	@Value("${optimal.module.manage.prefix-cloud-api}")
	private String prefixCloudApi;

	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	private final SwaggerProperties swaggerProperties;

	public OptimalManageAutoConfiguration(SwaggerProperties swaggerProperties) {
		this.swaggerProperties = swaggerProperties;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Bean
	public Docket swaggerSpringfoxApiDocket() {
		log.info("Starting Swagger");
		Contact contact = new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(),
				swaggerProperties.getContactEmail());

		ApiInfo apiInfo = new ApiInfo(swaggerProperties.getTitle(), swaggerProperties.getDescription(),
				swaggerProperties.getVersion(), swaggerProperties.getTermsOfServiceUrl(), contact,
				swaggerProperties.getLicense(), swaggerProperties.getLicenseUrl(), Collections.emptyList());

		Docket docket = new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(apiInfo).forCodeGeneration(true).directModelSubstitute(java.nio.ByteBuffer.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
		docket = docket.select().paths(regex("/.*"))
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).build();
		log.info("==>.Started Swagger in {} ms, config properties : {}");
		return docket;
	}
	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	@Configuration
	@Order(2)
	public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests().antMatchers(prefixCloudApi + "/**").permitAll().anyRequest().authenticated();
		}
	}

	@Configuration
	@Order(1)
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico", "/swagger-ui.html",
					"/webjars/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/configuration/security",
					"/swagger-resources/configuration/ui");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/login","/securityLogin","/createCode","/error/*").permitAll().anyRequest().authenticated()
//					.and().authorizeRequests().antMatchers(prefixCloudApi + "/**").permitAll().anyRequest().authenticated()
					.and().headers().frameOptions().sameOrigin()
					.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/", true)
					.and().logout().permitAll();
		}
	}

}
