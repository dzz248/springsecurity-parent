package springsecurity.security;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import springsecurity.entities.SysPermission;
import springsecurity.entities.SysUser;
import springsecurity.seivice.SysPermissionService;
import springsecurity.seivice.SysUserService;

import java.util.List;

/**
 *
 **/
public abstract class AbstractUserDetailsService implements UserDetailsService {

    @Autowired
    SysPermissionService sysPermissionService;
    @Autowired
    SysUserService sysUserService;


    /**
     * 交给子类实现查询用户信息
     *
     * @param usernameOrMobile 用户名或电话或邮箱
     * @return
     */
    public abstract SysUser findSysUser(String usernameOrMobile);

    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        // 1请求通过 用户名或手机 去数据库查询用户信息
        SysUser sysUser = findSysUser(usernameOrMobile);
        // 2. 查询该用户有哪一些权限
        findSysPermission(sysUser);
        // 4. 交给 Srpingsecurity进行身份认证
        return sysUser;

    }

    private void findSysPermission(SysUser sysUser) {

        List<SysPermission> permissions = sysPermissionService.findByUserId(sysUser.getId());
        if (CollectionUtils.isEmpty(permissions)) {
            return;
        }
        //左侧菜单动态渲染时会使用
        sysUser.setPermissions(permissions);
        // 3. 封装权限信息
        List<GrantedAuthority> authorities = Lists.newArrayList();
        permissions.forEach(sp ->
                //权限标识
                authorities.add(new SimpleGrantedAuthority(sp.getCode()))
        );
        sysUser.setAuthorities(authorities);


    }
}
