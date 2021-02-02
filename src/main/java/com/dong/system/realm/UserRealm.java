package com.dong.system.realm;

import com.dong.system.constant.Constant;
import com.dong.system.domain.SysUser;
import com.dong.system.service.IPermissionService;
import com.dong.system.service.IRoleService;
import com.dong.system.service.ISysUserService;
import com.dong.system.utils.ActiverUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/12 13:50
 * @Version: 1.0
 * @Description:
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    @Lazy
    private IRoleService roleService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.println(token.getPrincipal().toString());
        SysUser user=this.iSysUserService.queryUserByUserName(token.getPrincipal().toString());
        if(null!=user) {
            //创建ActiverUser
            ActiverUser activerUser=new ActiverUser();
            activerUser.setSysUser(user);
            if(user.getType()==Constant.USER_TYPE_NORMAL){
                //说明是普通用户
                List<String> roles= roleService.queryRoleNamesByUserId(user.getId());
                activerUser.setRoles(roles);
                //查询权限编码
                List<String> permissions=permissionService.queryPermissionsByUserId(user.getId());
                activerUser.setPermissions(permissions);
            }

            ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());//转化盐
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(activerUser, user.getPwd(), credentialsSalt, getName());

            return info;
        }
        return null;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new  SimpleAuthorizationInfo();
        ActiverUser activerUser=(ActiverUser) principalCollection.getPrimaryPrincipal();
        if(activerUser.getSysUser().getType()==Constant.USER_TYPE_SUPER) {
            info.addStringPermission("*:*");
        }else {
            List<String> roles = activerUser.getRoles();
            List<String> permissions = activerUser.getPermissions();
            if(roles!=null&&roles.size()>0) {
                info.addRoles(roles);
            }
            if(permissions!=null&&permissions.size()>0) {
                info.addStringPermissions(permissions);
            }
        }
        return info;
    }

}
