package springsecurity.properites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: 豆 www.mengxuegu.com
 */
@Component // 不要少了
@ConfigurationProperties(prefix = "myproject.security")
public class SecurityProperties {

    // 会将myproject.security.authentication 下面的值绑定到AuthenticationProperties对象上
    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
