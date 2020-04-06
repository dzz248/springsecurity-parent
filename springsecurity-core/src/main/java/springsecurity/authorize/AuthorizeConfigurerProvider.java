package springsecurity.authorize;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 关于授权配置统一接口 所有模块的授权配置类实现这个接口
 */
public interface AuthorizeConfigurerProvider {

    void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
