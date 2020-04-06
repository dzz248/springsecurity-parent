package springsecurity.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;
import springsecurity.authorize.AuthorizeConfigurerProvider;

/**
 * 关于系统管理模块的授权配置
 **/
@Component
public class SystemAuthorizeConfigureProvider implements AuthorizeConfigurerProvider {
    @Override
    public void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
//        config.antMatchers("/user").hasAuthority("sys:user") // 设置角色是会加上 ROLE_ 作为前缀 ROLE_ADMIN
//                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
//                .antMatchers(HttpMethod.GET, "/permission")
//                .access("hasAuthority('sys:permission') or hasAnyRole('ADMIN','ROOT')"); //有 权限 或者 角色是这个 都可以访问   给角色时要加ROLE_前缀


//        config.anyRequest().authenticated(); //身份认证模块写了 就不用再加 只用一个地方加
    }
}
