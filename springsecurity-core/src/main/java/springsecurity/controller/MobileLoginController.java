package springsecurity.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springsecurity.authentication.mobile.SmsSend;
import springsecurity.utils.ResultUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * 手机登录控制层
 **/
@Controller
public class MobileLoginController {

   public static final String SESSION_KEY ="SESSION_KEY_MOBILE_CODE";
@Autowired
   private SmsSend smsSend;

    @GetMapping("/mobile/page")
    public String toMobilePage(){
        return "login-mobile"; //templates/login-mobile.html
    }

    @GetMapping("/code/mobile")
    @ResponseBody
    public ResultUtil sendCode(HttpServletRequest request){
        //生成 手机验证码
        String code = RandomStringUtils.randomNumeric(4);
        //将验证码丢进session
        request.getSession().setAttribute(SESSION_KEY,code);
        //发送到手机
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile,code);
        return ResultUtil.ok();
    }
}
