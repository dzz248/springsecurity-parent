package springsecurity.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import springsecurity.authentication.code.ImageCodeValidateFilter;
import springsecurity.authentication.mobile.MobileAuthenticationConfig;
import springsecurity.authentication.mobile.MobileValidateFilter;
import springsecurity.properites.AuthenticationProperties;
import springsecurity.properites.SecurityProperties;
import springsecurity.session.CustomLogoutHandler;

import javax.sql.DataSource;


/**
 * alt+/ 导包
 * ctrl+o 覆盖
 *
 * @Auther: 梦学谷 www.mengxuegu.com
 */
@Configuration
@EnableWebSecurity // 开启springsecurity过滤链 filter
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Bean
    public PasswordEncoder passwordEncoder() {
        // 明文+随机盐值》加密存储
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserDetailsService customUserDetailsService;

    /**
     * 认证管理器：
     * 1. 认证信息（用户名，密码）
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 数据库存储的密码必须是加密后的，不然会报错：There is no PasswordEncoder mapped for the id "null"
//        String password = passwordEncoder().encode("1234");
//        logger.info("加密之后存储的密码：" + password);
//        auth.inMemoryAuthentication().withUser("mengxuegu")
//                .password(password).authorities("ADMIN");
        auth.userDetailsService(customUserDetailsService);
    }


    // 配置文件参数
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;
    //校验手机验证码
    @Autowired
    private MobileValidateFilter mobileValidateFilter;
    //校验手机号是否存在 手机认证
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy; //session管理
    /**
     * 当通个用户的Session数量超过指定值之后，会调用这个实现类
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    /**
     * 当你认证成功之后 ，springsecurity它会重写向到你上一次请求上
     * 资源权限配置：
     * 1. 被拦截的资源
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() // 采用 httpBasic认证方式
        http
                .addFilterBefore(mobileValidateFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单登录方式
                .loginPage(securityProperties.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理url, 默认是/login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) //账号属性名默认的是 username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter())  // 密码属性名默认的是 password
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests() // 认证请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage(),
                        securityProperties.getAuthentication().getImageCodeUrl(),
                        securityProperties.getAuthentication().getMobileCodeUrl(),
                        securityProperties.getAuthentication().getMobilePage()
                ).permitAll() // 放行/login/page 和 验证码 请求 /code/image不需要认证可访问
                .anyRequest().authenticated() //所有访问该应用的http请求都要通过身份认证才可以访问
                .and()
                .rememberMe()//记住我功能
                .tokenRepository(jdbcTokenRepository()) //保存登录信息
                .tokenValiditySeconds(securityProperties.getAuthentication().getTokenValiditySeconds())// 有效时长 秒
                .and()
                .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy) //当session失效处理
                .maximumSessions(1) //每个用户在系统中最多可以有多少个Session
                .expiredSessionStrategy(sessionInformationExpiredStrategy) // 单用户超过最大Session数执行实现类
//                .maxSessionsPreventsLogin(true) //当一个用户达到最大Session数，则不允许后面再登录了  (会引发一连串 不能登录的问题)
                .sessionRegistry(sessionRegistry())
                .and().and()
                .logout()
                .addLogoutHandler(customLogoutHandler) //退出处理
                .logoutUrl("/user/logout") //退出请求路径
                .logoutSuccessUrl("/mobile/page") //退出成功后跳转
                .deleteCookies("JSESSIONID") //退出后删除什么cookie值
        ; // 注意不要少了分号
        http.csrf().disable(); //关闭跨站请求伪造
        //将手机认证添加到过滤器链上
        http.apply(mobileAuthenticationConfig);
    }

    /**
     * 退出处理清除缓存
     */
    @Autowired
    private CustomLogoutHandler customLogoutHandler;
    /**
     * 为了解决退出重新登录失败
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Autowired
    DataSource dataSource;  //sql下的包

    //记住我功能
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //是否启动项目时自动创建表，true 自动创建
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 一般是针对静态资源放行
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
