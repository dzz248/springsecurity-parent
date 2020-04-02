package springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ReloadMessageConfig {
    @Bean //加载中文认证信息
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //.properties 不要加到后面
//        messageSource.setBasename("classpath:org\\springframework\\security\\messages_zh_CN");
        messageSource.setBasename("classpath:messages_zh_CN");
        return messageSource;
    }
}
