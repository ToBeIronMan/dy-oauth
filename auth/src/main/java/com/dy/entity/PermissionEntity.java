package com.dy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_permission")
public class PermissionEntity {
	@TableField("id")
	private Integer id;
	@ApiModelProperty("权限名称")
	@TableField("perName")
	private String permName;
	@ApiModelProperty("权限标识")
	@TableField("perTag")
	private String permTag;
	@ApiModelProperty("请求Url")
	@TableField("url")
	private String url;
}
