package com.dy.config;

import com.dy.component.JdbcTokenStoreUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;

/*
当前项目为认证授权中心
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;

    @Resource(name = "userDetailService")
    private UserDetailsService userDetailsService;
    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交 检查accessToken是否有效期的情况下
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    /**
     * 分配我们的appid和appkey clentid clientkey
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 读数据库
        clients.withClientDetails(clientDetailsService());

    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jdbcTokenStore()).authenticationManager(authenticationManager)
                //自定义AccessToken
                //.accessTokenConverter(accessTokenConverter)
                //设置userDetailsService
                .userDetailsService(userDetailsService)
//                //授权码储存
                .authorizationCodeServices(authorizationCodeServices())
                //设置userApprovalHandler
                .userApprovalHandler(userApprovalHandler())
                //设置tokenServices
                .tokenServices(tokenServices())
                //支持获取token方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.OPTIONS)
                //刷新token
                .reuseRefreshTokens(true);
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcClientDetailsService(dataSource);
    }
    @Bean
    public TokenStore jdbcTokenStore() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcTokenStore(dataSource);
    }
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    @Bean
    public UserApprovalHandler userApprovalHandler() {
        JdbcTokenStoreUserApprovalHandler approvalHandler = new JdbcTokenStoreUserApprovalHandler();
        approvalHandler.setTokenStore(jdbcTokenStore());
        approvalHandler.setClientDetailsService(clientDetailsService());
        approvalHandler.setRequestFactory(oAuth2RequestFactory());
        return approvalHandler;
    }
    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }
    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService());
        services.setSupportRefreshToken(true);
        /**
         * 此处设置没用，读数据库会改写有效时间，如果要设置，到oauth_client_details中设置
         */
        // token有效期自定义设置，24小时 60 * 60 * 24
        services.setAccessTokenValiditySeconds(60 * 60);
        //默认30天，这里修改 60 * 60 * 24 * 7
        services.setRefreshTokenValiditySeconds(60 * 60 * 24);
        services.setTokenStore(jdbcTokenStore());
        return services;
    }
}