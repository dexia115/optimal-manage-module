package com.optimal.kdm.module.manage.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ConfigurationProperties(prefix = "optimal.module.manage")
public class OptimalManageProperties {
	
	/**
     * 对应Cloud API在注册中心的服务名
     */
    private String cloudAppName;
    
    /**
     * 定义Cloud API前缀path
     */
    private String prefixCloudApi;

}
