package com.dy.service;


import com.dy.entity.dto.OauthClientDetailsDto;

public interface OAuthService {

    OauthClientDetailsDto loadOauthClientDetails(String clientId);
    Boolean checkOauthUrl(String url,Long id);

}
