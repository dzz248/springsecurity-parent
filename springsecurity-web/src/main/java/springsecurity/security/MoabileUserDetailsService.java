package springsecurity.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 通过手机号获取用户信息 和权限资源
 **/
@Log4j2
@Component("mobileUserDetailsService")
public class MoabileUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("需要验证的手机号："+mobile);
        //1通过 手机号查询用户信息

        //2.如果有用户信息，则在获取权限资源

        //3.封装用户信息


        return new User("meng","",true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
