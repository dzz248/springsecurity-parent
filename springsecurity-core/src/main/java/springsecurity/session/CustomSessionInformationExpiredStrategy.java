package springsecurity.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import springsecurity.authentication.CustomAuthenticationFailureHandler;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 单个用户Session 超过指定数量，会执行该类
 **/
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        // 1.获取用户名
        UserDetails userDetails = (UserDetails)event.getSessionInformation().getPrincipal();
        AuthenticationException exception =
                new AuthenticationServiceException(String.format("[%s]用户在另一地点登录，您被迫下线。",userDetails.getUsername()));
        try {
            //当用户在另一地点登录后，交给失败处理器回到认证页面
            event.getRequest().setAttribute("toAuthentication",true);
            customAuthenticationFailureHandler.onAuthenticationFailure(event.getRequest(),event.getResponse(),exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
