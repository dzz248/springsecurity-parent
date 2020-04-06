package springsecurity.security;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springsecurity.entities.SysPermission;
import springsecurity.entities.SysUser;
import springsecurity.seivice.SysPermissionService;
import springsecurity.seivice.SysUserService;

import java.util.List;

/**
 * 通过手机号获取用户信息 和权限资源
 **/
@Log4j2
@Component("mobileUserDetailsService")
public class MoabileUserDetailsService extends AbstractUserDetailsService {
    @Autowired
    SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String mobile) throws UsernameNotFoundException {
        log.info("需要验证的手机号：" + mobile);
        //1通过 手机号查询用户信息
        SysUser sysUser = sysUserService.findByMobile(mobile);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该手机号未注册");
        }
        return sysUser;

    }
}
