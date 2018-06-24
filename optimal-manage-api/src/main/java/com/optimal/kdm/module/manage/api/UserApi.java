package com.optimal.kdm.module.manage.api;

import com.optimal.kdm.common.utils.PageObj;

//import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"userApi"}, description = "用户信息查询模块")
//@FeignClient("${loyalty.module.coupon.cloud-app-name}")
public interface UserApi {
	
	@ApiOperation("分页查询用户列表信息")
	PageObj findUserByPage(Integer pageSize, Integer pageNo);

}
