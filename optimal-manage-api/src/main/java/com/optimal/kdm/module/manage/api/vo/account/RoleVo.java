package com.optimal.kdm.module.manage.api.vo.account;

import java.util.List;
import lombok.Data;

@Data
public class RoleVo {
	
	private Long id;
	
	private String name;
	
	private String code;
	
	private String details;
	
	private Boolean enable;
	
	private List<Long> authorityIds;
	
	private String authStr;
	
	private List<AuthorityVo> authorityVos;
	
	public RoleVo(){
		
	}
	
	public RoleVo(Long id,String name,String code,String details){
		this.id = id;
		this.name = name;
		this.code = code;
		this.details = details;
	}


}
