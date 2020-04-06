package springsecurity.authorize;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import springsecurity.properites.SecurityProperties;

/**
 * 身份认证相关授权配置
 */
@Component
@Order(Integer.MAX_VALUE) // 值最大 最后加载  config.anyRequest().authenticated(); 要最后加载
public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(securityProperties.getAuthentication().getLoginPage(),
                securityProperties.getAuthentication().getImageCodeUrl(),
                securityProperties.getAuthentication().getMobileCodeUrl(),
                securityProperties.getAuthentication().getMobilePage()
        ).permitAll();// 放行/login/page 和 验证码 请求 /code/image不需要认证可访问

        //所有访问该应用的http请求都要通过身份认证才可以访问
        config.anyRequest().authenticated();
    }

}
