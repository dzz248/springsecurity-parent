package springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import springsecurity.entities.SysPermission;

import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    @Select("SELECT DISTINCT\n" +
            "\t p.id, p.parent_id, p.name, p.code, p.url, p.type, p.icon, p.remark, p.create_date, p.update_date\n" +
            "FROM\n" +
            "\tsys_user u\n" +
            "\tLEFT JOIN sys_user_role ur ON u.id = ur.user_id\n" +
            "\tLEFT JOIN sys_role r on r.id =ur.role_id\n" +
            "\tLEFT JOIN sys_role_permission rp ON rp.role_id =r.id\n" +
            "\tLEFT JOIN sys_permission p on p.id =rp.permission_id \n" +
            "\tWHERE u.id = #{userId} \n" +
            "\t")
    List<SysPermission> selectParmissionByUserId(@Param("userId") Long userId);
}
