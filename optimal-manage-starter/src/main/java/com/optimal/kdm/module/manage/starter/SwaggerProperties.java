package com.optimal.kdm.module.manage.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@Data
@ConfigurationProperties(prefix = "optimal.swagger")
public class SwaggerProperties {
	private String title = "Optimal Application API";
	private String description = "API documentation";
	private String version = "1.0.0";
	private String termsOfServiceUrl;
	private String contactName = "Optimal";
	private String contactUrl = "http://www.koudaimiao.com";
	private String contactEmail = "dexia115@sina.com";
	private String license;
	private String licenseUrl;
	private String defaultIncludePattern = "/.*";
	private String basePackage = "com.optimal.kdm.module.manage.biz";
	private String host;
}
