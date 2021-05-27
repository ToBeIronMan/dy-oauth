package com.dy.filter;

import com.dy.utils.MayiktJwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 拦截请求
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication
                (setAuthentication(request.getHeader("jwt")));
        super.doFilterInternal(request, response, chain);;
    }

    private UsernamePasswordAuthenticationToken setAuthentication(String token) {
        String username = MayiktJwtUtils.getUsername(token);
        if (!StringUtils.isEmpty(username)) {
            // 解析权限列表
            List<SimpleGrantedAuthority> userRoleList = MayiktJwtUtils.getUserRole(token);
            return new UsernamePasswordAuthenticationToken(username, null, userRoleList);
        }
        return null;
    }
}