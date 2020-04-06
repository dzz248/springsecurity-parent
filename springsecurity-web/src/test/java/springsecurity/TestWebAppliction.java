package springsecurity;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springsecurity.entities.SysPermission;
import springsecurity.seivice.SysPermissionService;
import springsecurity.seivice.SysUserService;

import java.util.List;

/**
 *
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWebAppliction {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysPermissionService sysPermissionService;

    @Test
    public void test() {
       List<SysPermission> res = sysPermissionService.findByUserId(5L);
        System.out.println(res.size()+"???>>>>>");
    }

}
