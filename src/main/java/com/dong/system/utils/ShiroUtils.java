package com.dong.system.utils;

import com.dong.system.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;


public class ShiroUtils {

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static ActiverUser getUser() {
        Object object = getSubjct().getPrincipal();
        ActiverUser user =null;
        if(object!=null){
           user = new ActiverUser();
            BeanUtils.copyProperties(object,user);
        }

        return user;
    }
    public static int getUserId() {
        if(getUser()!=null)
        {
            return  getUser().getSysUser().getId();
        }else{
            return 0;
        }

    }
    public static void logout() {
        getSubjct().logout();
    }


}
