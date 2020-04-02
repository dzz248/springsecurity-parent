package springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户管理
 **/
@Controller
@RequestMapping("/user")
public class SysPermissionController {
    private static final String HTML_PREFIX = "system/user/";


    @GetMapping({"/", ""})
    public String user() {
        return HTML_PREFIX + "user-list";
    }
}
