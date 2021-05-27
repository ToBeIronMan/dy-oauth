package com.dy.controller;

import com.dy.entity.dto.UrlOauth;
import com.dy.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
   private OAuthService oAuthService;
    @PostMapping("/user/checkOauthUrls")
    public Boolean checkOath(@RequestBody UrlOauth urlOauth){

        return oAuthService.checkOauthUrl(urlOauth.getUrl(),urlOauth.getUserId());
    }
}
