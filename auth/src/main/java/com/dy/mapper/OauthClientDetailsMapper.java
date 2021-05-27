package com.dy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dy.entity.OauthClientDetails;
import com.dy.entity.dto.OauthClientDetailsDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {
    @Select("select * from oauth_client_details where client_id =#{clientId}")
    OauthClientDetailsDto loadOauthClientDetailsByClientId(String clientId);
    @Select(" select permission.* from sys_user user" + " inner join sys_user_role user_role"
            + " on user.id = user_role.user_id inner join "
            + "sys_role_permission role_permission on user_role.role_id = role_permission.role_id "
            + " inner join sys_permission permission on role_permission.perm_id = permission.id where user.id = #{id}" +
            " and permission.url= #{url} ;")
    Boolean cpAuthUrl(@Param("id")Long id, @Param("url")String url);
}
