package com.optimal.kdm.module.manage.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.optimal.kdm.common.utils.Groups;
import com.optimal.kdm.common.utils.PageObj;
import com.optimal.kdm.module.manage.api.UserApi;
import com.optimal.kdm.module.manage.biz.entity.account.User;
import com.optimal.kdm.module.manage.biz.service.AccountService;

@RestController
@RequestMapping("${optimal.module.manage.prefix-cloud-api}/user")
public class UserApiController implements UserApi{
	
	@Autowired
	private AccountService accountService;

	@GetMapping("findUserByPage/{pageSize}/{pageNo}")
	@Override
	public PageObj findUserByPage(@PathVariable("pageSize") Integer pageSize, @PathVariable("pageNo") Integer pageNo) {
		PageObj<User> page = new PageObj<User>(pageSize,pageNo);
		Groups groups = new Groups();
		groups.Add("enable",true);
		
		return accountService.findUserPageByGroups(groups, page);
	}

}
