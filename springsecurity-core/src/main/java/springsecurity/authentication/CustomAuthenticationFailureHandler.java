package springsecurity.authentication;


import base.result.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import springsecurity.properites.LoginResponseType;
import springsecurity.properites.SecurityProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理失败认证的
 *
 * @Auther: 豆 www.mengxuegu.com
 */
@Slf4j
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    SecurityProperties securityProperties;

    /**
     * @param exception 认证失败时抛出异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            // 认证失败响应JSON字符串，
            ResultUtils result = ResultUtils.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            // 重写向回认证页面，注意加上 ?error
//            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
            String referer = request.getHeader("Referer");
            log.info("referer:" + referer);
            //如果下面有值 则认为是多端登录 直接返回登录地址
            Object toAuthentication = request.getAttribute("toAuthentication");
            String lastUrl = toAuthentication != null ?
                    securityProperties.getAuthentication().getLoginPage() : StringUtils.substringBefore(referer, "?");

            log.info("上一次请求的路径:" + lastUrl);
            super.setDefaultFailureUrl(lastUrl + "?error");
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
