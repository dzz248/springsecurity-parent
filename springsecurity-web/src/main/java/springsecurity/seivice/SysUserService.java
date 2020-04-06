package springsecurity.seivice;

import com.baomidou.mybatisplus.extension.service.IService;
import springsecurity.entities.SysUser;

public interface SysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 通过手机号查询用户信息
     */
    SysUser findByMobile(String mobile);


}
