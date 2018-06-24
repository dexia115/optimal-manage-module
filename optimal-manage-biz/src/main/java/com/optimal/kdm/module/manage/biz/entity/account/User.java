package com.optimal.kdm.module.manage.biz.entity.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.optimal.kdm.common.data.entity.BaseEntity;
import com.optimal.kdm.common.utils.CommonUtil;
import com.optimal.kdm.module.manage.api.vo.account.RoleVo;
import com.optimal.kdm.module.manage.api.vo.account.UserVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "account_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 4285275098457224134L;

	@Column(name = "mobile", unique = true)
	private String mobile;

	@Column(name = "real_name")
	private String realName;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "password")
	private String password;

	// 身份证号
	@Column(length = 50)
	private String idcard;

	// 头像
	@Column(length = 50, name = "head_photo")
	private String headPhoto;

	@Column(name = "create_time")
	private Date createTime = new Date();
	
	//是否是初始化（如果是就要求用户更新密码）
	@Column(name = "is_init")
	private Boolean isInit = false;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	public User() {
		// 保留缺省构造方法
	}

	public User(Long id) {
		this.id = id;
	}

	public User(Long id, String userName, String password, String realName, String idcard, Boolean enable) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.idcard = idcard;
		this.enable = enable;
	}

	public UserVo toVo() {
		UserVo userVo = new UserVo(id, userName, password, mobile);
		userVo.setIdcard(idcard);
		userVo.setEnable(enable);
		userVo.setRealName(realName);
		userVo.setCreateTime(CommonUtil.getNow(2, createTime));
		if(!roles.isEmpty()){
			StringBuffer sBuffer = new StringBuffer();
			List<Long> roleIds = new ArrayList<>();
			List<RoleVo> roleVoList = new ArrayList<>();
			for(Role role : roles){
				sBuffer.append(role.getName() + ",");
				roleIds.add(role.getId());
				roleVoList.add(role.toVo());
			}
			sBuffer.deleteCharAt(sBuffer.length() - 1);
			String roleStr = sBuffer.toString();
			userVo.setRoleStr(roleStr);
			userVo.setRoles(roleIds);
			userVo.setRoleVoList(roleVoList);
		}
		
		return userVo;
	}

	public static User fromVo(UserVo vo) {
		User user = new User(vo.getId(), vo.getUserName(), vo.getPassword(), vo.getRealName(),vo.getIdcard(),vo.getEnable());
		user.setMobile(vo.getMobile());
		List<Long> roleIds = vo.getRoles();
		List<Role> roles = new ArrayList<>();
		for(Long roleId : roleIds){
			roles.add(new Role(roleId));
		}
		user.setRoles(roles);
		return user;
	}

	public static List<UserVo> toVoList(List<User> userList) {
		List<UserVo> detailsList = new ArrayList<>();
		if (userList != null) {
			userList.stream().forEach(user -> detailsList.add(user.toVo()));
		}
		return detailsList;
	}


}
