package springsecurity.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 模仿UsernamePasswordAuthenticationToken
 **/
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    // ~ Instance fields
    // ================================================================================================

    private final Object principal; //认证前 是 手机号码 认证后是用户信息

    // ~ Constructors
    // ===================================================================================================

    /**
     * 认证之前使用的构造方法 此方法会标识未认证
     *
     */
    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    /**
     *
     *认证通过好 会重新创建 MobileAuthenticationToken 实例 来进行封装认证信息
     * @param principal  用户信息
     * @param authorities 权限资源
     */
    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    //因为它是父类中的抽象方法 所以要实现 直接返回空
    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    //设置权限资源
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

}
