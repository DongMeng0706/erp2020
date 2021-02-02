package com.dong.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/14 12:48
 * @Version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/system")
public class SystemPageController {

    /**
     * 跳转到登陆页面
     */
    @RequestMapping("toLogin")
    public String toLoginPage() {
        return "login";
    }

    /**
     * 跳转到系统主页
     */
    @RequestMapping("index")
    public String homePage() {
        return "system/index";
    }
    /**
     * 跳转到首页工作台页面
     * @return
     */
    @RequestMapping("toDeskManager")
    public String toDeskManager(){
        return "system/deskManager";
    }

    /**
     * 跳转到通知公告管理页面
     * @return
     */
    @RequestMapping("toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    @RequestMapping("toNoticeSaveOrUpdate")
    public String toNoticeSaveOrUpdate(){
        return "system/notice/noticeSaveOrUpdate.html";
    }


    /**
     * 跳转到系统日志查询页面
     * @return
     */
    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }

    /**
     * 跳转到部门管理页面
     * @return
     */
    @RequestMapping("toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * 跳转到部门管理页面-左边的树页面
     * @return
     */
    @RequestMapping("toDeptLeftManager")
    public String toDeptLeftManager(){
        return "system/dept/deptLeftManager";
    }

    /**
     *  跳转到部门管理页面-右边的数据列表
     * @return
     */
    @RequestMapping("toDeptRightManager")
    public String toDeptRightManager(){
        return "system/dept/deptRightManager";
    }


    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理页面-左边的树页面
     * @return
     */
    @RequestMapping("toMenuLeftManager")
    public String toMenuLeftManager(){
        return "system/menu/menuLeftManager";
    }

    /**
     *  跳转到菜单管理页面-右边的数据列表
     * @return
     */
    @RequestMapping("toMenuRightManager")
    public String toMenuRightManager(){
        return "system/menu/menuRightManager";
    }

    /**
     *  跳转到角色管理
     */
    @RequestMapping("toRoleManager")
    public String toRoleManager(){
        return "system/role/roleManager";
    }


    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理页面-左边的树页面
     * @return
     */
    @RequestMapping("toPermissionLeftManager")
    public String toPermissionLeftManager(){
        return "system/permission/permissionLeftManager";
    }

    /**
     *  跳转到权限管理页面-右边的数据列表
     * @return
     */
    @RequestMapping("toPermissionRightManager")
    public String toPermissionRightManager(){
        return "system/permission/permissionRightManager";
    }

    /**
     * 跳转用户管理界面
     */
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }

    /**
     * 跳转到用户信息修改页面
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo(){
        return "system/user/userInfo";
    }
}
