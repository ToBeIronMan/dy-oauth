package com.dy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 角色信息表
@Data
@TableName("sys_role")
public class RoleEntity {
	@TableField("id")
	private Integer id;
	@ApiModelProperty("角色名字")
	@TableField("roleName")
	private String roleName;
	@ApiModelProperty("角色描述")
	@TableField("roleDesc")
	private String roleDesc;
}
