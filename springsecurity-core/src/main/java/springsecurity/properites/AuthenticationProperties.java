package springsecurity.properites;

import lombok.Data;

/**
 * @Auther: 豆 www.mengxuegu.com
 */
@Data
public class AuthenticationProperties {

    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

    /**
     * 认证响应的类型： JSON 、 REDIRECT 重定向
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    private String imageCodeUrl = "/code/image"; //获取图形验证码地址
    private String mobileCodeUrl = "/code/mobile";  //获取手机验证码请求
    private String mobilePage = "/mobile/page";   //前往手机登录页面
    private Integer tokenValiditySeconds = 3600;   //记住我功能有效时长 秒
}
