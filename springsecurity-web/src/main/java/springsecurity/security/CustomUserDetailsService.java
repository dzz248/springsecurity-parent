package springsecurity.security;

import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import springsecurity.entities.SysPermission;
import springsecurity.entities.SysUser;
import springsecurity.mapper.SysUserMapper;
import springsecurity.seivice.SysPermissionService;
import springsecurity.seivice.SysUserService;

import java.util.List;

/**
 * 查询数据库中的用户信息
 *
 * @Auther: 豆 www.mengxuegu.com
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 不能删掉 否则报这个 而且手机或用户名验证只能注入一个
     * Error creating bean with name 'sessionRegistry':
     * Requested bean is currently in creation: Is there an unresolvable circular reference?
     */
    @Autowired //不能删掉
    PasswordEncoder passwordEncoder;

    @Autowired
    SysUserService sysUserService;



    @Override
    public SysUser findSysUser(String username) throws UsernameNotFoundException {
        logger.info("请求认证的用户名: " + username);

        // 1. 通过请求的用户名去数据库中查询用户信息
        SysUser sysUser = sysUserService.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return sysUser;

    }

}
