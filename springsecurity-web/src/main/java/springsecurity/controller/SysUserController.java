package springsecurity.controller;

import org.assertj.core.util.Lists;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springsecurity.utils.ResultUtil;

import java.util.List;

/**
 * 用户管理
 **/
@Controller
@RequestMapping("/user")
public class SysUserController {
    private static final String HTML_PREFIX = "system/user/";

    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping({"/", ""})
    public String user() {
        return HTML_PREFIX + "user-list";
    }

    /**
     * 跳转新增或者修改页面
     *
     * @return
     */
    // 有'sys:user:add' 或 'sys:user:edit' 权限的用户可以访问
    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @RequestMapping(value = {"/form"})
    public String form() {
        return HTML_PREFIX + "user-form";
    }

    //返回值的code等于200 则调用成功有权限，否则返回403
    @PostAuthorize("returnObject.code == 200")
    @RequestMapping("/{id}")
    @ResponseBody
    public ResultUtil deleteById(@PathVariable Long id) {
        if(id<0){
            return ResultUtil.build(500,"失败",id);
        }
        return ResultUtil.ok();
    }
    //参数过滤 filterTarget 指定那个参数 filterObject指集合中的每个元素
    //如果value表达式为True 数据则不会被过滤， 否则过滤
    @PreFilter(filterTarget = "ids",value = "filterObject > 0")
    @RequestMapping("/batch/{ids}")
    @ResponseBody
    public ResultUtil deleteByIds(@PathVariable List<Long> ids) {
        return ResultUtil.ok(ids);
    }

    //返回值：filterObject是返回值集合中的每个元素 ，当表达式为True 则对应元素返回
    @PostFilter(value = "filterObject != authentication.principal.username")
    @RequestMapping("/list")
    @ResponseBody
    public List<String> page() {
        List<String> userList = Lists.newArrayList("meng","xue","gu");
        return  userList;
    }
}
