package com.optimal.kdm.module.manage.api.vo.account;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class AuthorityVo {
	
	private Long id;
	
	private String name;
	
	private String code;
	
	private String details;
	
	private String url;
	
	private Integer sort = 0;
	
	private Integer method;
	
	private String icons;
	
	private Long parentId;
	
	private String parentName;
	
	private List<AuthorityVo> children = new ArrayList<AuthorityVo>();
	
	
	public AuthorityVo(){
		
	}
	
	public AuthorityVo(Long id, String name, String code, String details, String url, Integer sort, Integer method,
			String icons, Long parentId, String parentName) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.details = details;
		this.url = url;
		this.sort = sort;
		this.method = method;
		this.icons = icons;
		this.parentId = parentId;
		this.parentName = parentName;
	}

}
