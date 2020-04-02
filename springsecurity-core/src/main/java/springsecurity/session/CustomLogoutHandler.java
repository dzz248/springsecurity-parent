package springsecurity.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出系统处理类  解决退出后Session不删除
 **/
@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
       //退出系统后，将对应Session从缓存中删除
        sessionRegistry.removeSessionInformation(request.getSession().getId());
    }
}
