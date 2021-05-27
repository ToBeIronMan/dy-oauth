package com.dy.service.impl;

import com.dy.entity.dto.OauthClientDetailsDto;
import com.dy.mapper.OauthClientDetailsMapper;
import com.dy.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    OauthClientDetailsMapper oAuthDao;

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = true)
    public OauthClientDetailsDto loadOauthClientDetails(String clientId) {
        return oAuthDao.loadOauthClientDetailsByClientId(clientId);
    }

    @Override
    public Boolean checkOauthUrl(String url, Long id) {
        return oAuthDao.cpAuthUrl(id,url);
    }


}
