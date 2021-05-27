package com.dy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// 用户信息表
@Data
@TableName("sys_user")
public class UserEntity implements UserDetails {
    @TableField("id")
	private Integer id;
    @ApiModelProperty("用户名")
    @TableField("username")
	private String username;
    @ApiModelProperty("真实姓名")
    @TableField("realname")
	private String realname;
    @ApiModelProperty("密码")
	@TableField("password")
	private String password;
    @ApiModelProperty("创建日期")
	@TableField("createDate")
	private Date createDate;
    @ApiModelProperty("最后等陆时间")
	@TableField("lastLoginTime")
	private Date lastLoginTime;
    @ApiModelProperty("是否启用")
	@TableField("enabled")
	private boolean enabled;
	@TableField("accountNonExpired")
	private boolean accountNonExpired;
	@TableField("accountNonLocked")
	private boolean accountNonLocked;
	@TableField("credentialsNonExpired")
	private boolean credentialsNonExpired;
	// 用户所有权限
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

}
