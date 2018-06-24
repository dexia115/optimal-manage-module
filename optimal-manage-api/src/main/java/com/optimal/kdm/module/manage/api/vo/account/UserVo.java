package com.optimal.kdm.module.manage.api.vo.account;

import java.util.List;

import lombok.Data;

@Data
public class UserVo {
	
	private Long id;
	
	private String userName;
	
	private String password;
	
	private String mobile;
	
	private String idcard;
	
	private String realName;
	
	private Boolean enable = true;
	
	private List<Long> roles;
	
	private List<RoleVo> roleVoList;
	
	private String roleStr;
	
	private String createTime;
	
	public UserVo(){
		
	}
	
	public UserVo(Long id,String userName,String password,String mobile){
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.mobile = mobile;
	}


}
