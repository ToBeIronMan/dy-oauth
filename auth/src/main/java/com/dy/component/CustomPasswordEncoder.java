package com.dy.component;

import com.dy.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 *   自定义PasswordEncoder
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/24 17:02  修改内容:
 * </pre>
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    /**
     * 对密码MD5
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String) rawPassword);
    }

    /**
     * rawPassword 用户输入的密码
     * encodedPassword 数据库DB的密码
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String rawPass = MD5Util.encode((String) rawPassword);
        boolean result = rawPass.equals(encodedPassword);
        return result;
    }
}

