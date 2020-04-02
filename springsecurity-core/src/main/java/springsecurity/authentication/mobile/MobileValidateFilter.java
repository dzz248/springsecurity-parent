package springsecurity.authentication.mobile;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springsecurity.authentication.CustomAuthenticationFailureHandler;
import springsecurity.controller.CustomLoginController;
import springsecurity.controller.MobileLoginController;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机验证码 校验
 **/
@Component
public class MobileValidateFilter extends OncePerRequestFilter {
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //判断 请求是否为手机登录 且POST请求
        if ("/mobile/form".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            try {
                // 校验验证码合法性
                validate(request);
            }catch (AuthenticationException e) {
                // 交给失败处理器进行处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 一定要记得结束
                return;
            }
        }
        // 放行请求
        filterChain.doFilter(request, response);
    }


    private void  validate(HttpServletRequest request) {
        // 先获取seesion中的验证码
        String sessionCode =
                (String)request.getSession().getAttribute(MobileLoginController.SESSION_KEY);
        // 获取用户输入的验证码
        String inpuCode = request.getParameter("code");
        // 判断是否正确
        if(StringUtils.isBlank(inpuCode)) {
            throw new springsecurity.authentication.code.ValidateCodeException("验证码不能为空");
        }

        if(!inpuCode.equalsIgnoreCase(sessionCode)) {
            throw new springsecurity.authentication.code.ValidateCodeException("验证码输入错误");
        }
    }
}
