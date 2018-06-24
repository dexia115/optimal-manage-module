package com.optimal.kdm.module.manage.biz.entity.account;

import javax.persistence.*;
import com.optimal.kdm.common.data.entity.BaseEntity;
import com.optimal.kdm.module.manage.api.vo.account.AuthorityVo;
import com.optimal.kdm.module.manage.api.vo.account.RoleVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "account_role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = -9196730643894509307L;

	@Column(name = "name", length = 20)
	private String name;
	
	@Column(length = 20)
	private String code;

	// 描述
	@Column(length = 20)
	private String details;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.DETACH)
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_authority")
	@OrderBy(value = "parent_id ASC,sort ASC")
	private List<Authority> authoritys;

	public Role() {
		// 保留缺省构造方法
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(Long id, String name, String code, String details) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.details = details;
	}

	public RoleVo toVo() {
		RoleVo roleVo = new RoleVo(id, name, code, details);
		roleVo.setEnable(enable);
		List<Long> authorityIds = new ArrayList<>();
		List<AuthorityVo> authorityVos = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		if(!authoritys.isEmpty()){
			for(Authority auth : authoritys){
				authorityIds.add(auth.getId());
				authorityVos.add(auth.toVo());
				if(auth.getMethod()==1 && auth.getParent() != null){
					sb.append(auth.getName()+",");
				}
			}
			if(sb.length() > 1){
				sb.deleteCharAt(sb.length()-1);
			}
			roleVo.setAuthStr(sb.toString());
		}
		roleVo.setAuthorityIds(authorityIds);
		roleVo.setAuthorityVos(authorityVos);
		return roleVo;
	}
	
	public static Role fromVo(RoleVo vo){
		return new Role(vo.getId(),vo.getName(),vo.getCode(),vo.getDetails());
	}

	public static List<RoleVo> toVoList(List<Role> roleList) {
		List<RoleVo> detailsList = new ArrayList<>();
		if (roleList != null) {
			roleList.stream().forEach(role -> detailsList.add(role.toVo()));
		}
		return detailsList;
	}

	
}
