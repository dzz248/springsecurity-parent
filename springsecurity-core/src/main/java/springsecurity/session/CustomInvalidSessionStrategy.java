package springsecurity.session;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import springsecurity.utils.ResultUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效后的处理逻辑
 **/
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    private SessionRegistry sessionRegistry;

    public CustomInvalidSessionStrategy(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //删除系统缓存中的session （非REDIS） 不能直接用request.getSession.getId()
                sessionRegistry.removeSessionInformation(request.getRequestedSessionId());
        //将浏览器中的cookie的jessionid删除 不然重新刷新页面 一直超时
        cancelCookie(request,response);
        ResultUtil resultUtil = new ResultUtil().build(
                HttpStatus.UNAUTHORIZED.value(), "登录已超时：请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(resultUtil.toJsonString());
    }

    /**
     * 清除cookie
     * @param request
     * @param response
     */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", (String) null);
        cookie.setMaxAge(0);
        cookie.setPath(this.getCookiePath(request));
        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}
