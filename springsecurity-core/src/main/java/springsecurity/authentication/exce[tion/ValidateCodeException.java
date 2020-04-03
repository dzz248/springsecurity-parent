package springsecurity.authentication.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码相关异常类
 * @Auther: 豆 www.mengxuegu.com
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
