package com.dy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("oauth_client_details")
public class OauthClientDetails implements Serializable {

    @TableField("client_id")
    private String clientId;

    @TableField("resource_ids")
    private String resourceIds;

    /**
     * Encrypted
     */
    @TableField("clientSecret")
    private String clientSecret;
    /**
     * Available values: read,write
     */
    @TableField("scope")
    private String scope;

    /**
     * grant types include
     * "authorization_code", "password", "assertion", and "refresh_token".
     * Default value is "authorization_code,refresh_token".
     */
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,refresh_token";

    /**
     * The re-direct URI(s) established during registration (optional, comma separated).
     */
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <p/>
     * For example: ROLE_USER
     */
    @TableField("authorities")
    private String authorities;

    /**
     * The access token validity period in seconds (optional).
     * If unspecified a global default will be applied by the token services.
     */
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    /**
     * The refresh token validity period in seconds (optional).
     * If unspecified a global default will  be applied by the token services.
     */
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    // optional
    @TableField("additional_information")
    private String additionalInformation;

    /**
     * Value is 'true' or 'false',  default 'false'
     */
    @TableField("auto_approve")
    private String autoApprove;
}