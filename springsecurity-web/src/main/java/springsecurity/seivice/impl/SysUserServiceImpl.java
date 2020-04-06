package springsecurity.seivice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import springsecurity.entities.SysUser;
import springsecurity.mapper.SysUserMapper;
import springsecurity.seivice.SysUserService;

/**
 *
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public SysUser findByUsername(String username) {
        if(StringUtils.isEmpty(username)){
            return null;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser findByMobile(String mobile) {
        if(StringUtils.isEmpty(mobile)){
            return null;
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobile);

        return baseMapper.selectOne(queryWrapper);
    }
}
