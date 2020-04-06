package springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.entities.SysPermission;
import springsecurity.seivice.SysPermissionService;
import springsecurity.utils.ResultUtil;

import java.util.List;

/**
 * 角色管理
 **/
@Controller
@RequestMapping("/permission")
public class SysPermissionController {
    private static final String HTML_PREFIX = "system/permission/";

    @Autowired
    SysPermissionService sysPermissionService;

    @GetMapping({"/", ""})
    @PreAuthorize(value = "hasAuthority('sys:permission')")
    public String user() {
        return HTML_PREFIX + "permission-list";
    }

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:permission:list')")
    @ResponseBody
    public ResultUtil list() {
        List<SysPermission> list = sysPermissionService.list();
        return ResultUtil.ok(list);
    }

    /**
     * 跳转新增或者修改页面
     * @return
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:edit','sys:permission:add')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        return HTML_PREFIX + "permission-form";
    }
}
