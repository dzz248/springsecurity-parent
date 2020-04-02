package springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import springsecurity.authentication.mobile.SmsCodeSender;
import springsecurity.authentication.mobile.SmsSend;
import springsecurity.session.CustomInvalidSessionStrategy;
import springsecurity.session.CustomSessionInformationExpiredStrategy;

/**
 * 主要为容器中添加Bean实力
 *
 * @ConditionalOnMissingBean(T.class) 默认情况下 采用T作为实例，如果有其他实现类 这个就失效
 **/
@Configuration
public class SecurityConfigBean {
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionInformationExpiredStrategy();
    }

    /**
     * 登录超时处理
     *
     * @return
     */
    @Autowired
    private SessionRegistry sessionRegistry;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy(sessionRegistry);
    }

    /**
     * 注册 短信服务 等价于在实现类直接注入@Component
     *
     * @ConditionalOnMissingBean 默认情况下 采用SmsCodeSender作为实例，如果有其他实现类 这个就失效
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }
}
