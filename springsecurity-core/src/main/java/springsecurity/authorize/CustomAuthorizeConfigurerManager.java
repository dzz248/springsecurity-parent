package springsecurity.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将所有授权配置管理起来
 **/
@Component
public class CustomAuthorizeConfigurerManager implements AuthorizeConfigurerManager {
    @Autowired
    List<AuthorizeConfigurerProvider> authorizeConfigurerProviders; //所有的实现类都注入

    // 将所有 AuthorizeConfigurerProvider的实现类 传入配置的参数
    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
//        for(AuthorizeConfigurerProvider provider:authorizeConfigurerProviders){
//            provider.confiure(config);
//        }
        authorizeConfigurerProviders.forEach(provider ->
            provider.confiure(config)
        );
    }
}
