package springsecurity.seivice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import springsecurity.entities.SysPermission;
import springsecurity.mapper.SysPermissionMapper;
import springsecurity.seivice.SysPermissionService;

import java.util.List;

/**
 *
 **/
@Service
public class SysPermissionServiceIml extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
    @Override
    public List<SysPermission> findByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        List<SysPermission> list = baseMapper.selectParmissionByUserId(userId);
        //如果没权限则将集合中的数据Null移除
        list.remove(null);
        return list;
    }
}
