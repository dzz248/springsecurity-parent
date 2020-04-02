package springsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MainController {

    @RequestMapping({"/", "/index", ""})
    public String index(Map<String, Object> res) {
      //获取当前登录信息1
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            res.put("username", username);
        }
        return "index";
    }

    @ResponseBody
    @RequestMapping("/user/info")
    public Object userInfo(Authentication authentication) {
        //获取当前登录信息2
        Object principal = authentication.getPrincipal();
        return principal;
    }
    @ResponseBody
    @RequestMapping("/user/info2")
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails){
        //获取当前登录信息3
        return userDetails;
    }
}
