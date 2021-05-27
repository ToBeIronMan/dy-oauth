package com.dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dy.entity.PermissionEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<PermissionEntity> {
    @Select(" select * from sys_permission ")
    List<PermissionEntity> findAllPermission();
}
