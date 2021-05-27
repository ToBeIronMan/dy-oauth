package com.dy.service.impl;


import com.dy.entity.UserEntity;
import com.dy.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    Logger LOG = LoggerFactory.getLogger(com.dy.service.impl.UserDetailServiceImpl.class);

    @Autowired
    private UserMapper userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class,readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDao.findByUsername(username);
        if(user == null){
            //LOG.info("登录用户[{}]没注册!",username);
            throw new UsernameNotFoundException("登录用户["+username + "]没注册!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
