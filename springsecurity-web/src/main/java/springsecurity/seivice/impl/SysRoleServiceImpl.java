package springsecurity.seivice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springsecurity.entities.SysRole;
import springsecurity.mapper.SysRoleMapper;
import springsecurity.seivice.SysRoleService;

/**
 *
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
