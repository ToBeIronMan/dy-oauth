package com.dy.config;

import com.dy.exception.MyAccessDeniedHandler;
import com.dy.exception.MyOAuth2AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 资源Server端
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {


    private String mayiktAppId="cms";

    private String mayiktAppSecret="secret";
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Primary
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        //设置授权服务器check_token端点完整地址
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8087/oauth/check_token");
        //设置客户端id与secret，注意：client_secret值不能使用passwordEncoder加密！
        tokenServices.setClientId(mayiktAppId);
        tokenServices.setClientSecret(mayiktAppSecret);
        return tokenServices;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] urls = new String[filterIgnorePropertiesConfig.getUrls().size()];
        urls = filterIgnorePropertiesConfig.getUrls().toArray(urls);
//        //设置创建session策略
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        //@formatter:off
        //所有请求必须授权
        System.out.println("人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人人");
        System.out.println(urls);
        http.authorizeRequests()
                .antMatchers("/oauth/**","/auth/**").
                permitAll()
                .anyRequest().authenticated();
        //@formatter:on
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) throws Exception {
        configurer
                .resourceId("resource1").stateless(true);
//                .authenticationEntryPoint(new MyOAuth2AuthenticationEntryPoint())
//                .accessDeniedHandler(new MyAccessDeniedHandler());
    }
}