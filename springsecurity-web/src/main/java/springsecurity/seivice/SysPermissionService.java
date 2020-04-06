package springsecurity.seivice;

import com.baomidou.mybatisplus.extension.service.IService;
import springsecurity.entities.SysPermission;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 通过用户id查询所有权限
     * @param userId
     * @return
     */
    List<SysPermission> findByUserId(Long userId);
}
