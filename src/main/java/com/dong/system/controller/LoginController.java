package com.dong.system.controller;

import com.dong.system.constant.Constant;
import com.dong.system.domain.Loginfo;
import com.dong.system.domain.Permission;
import com.dong.system.domain.SysUser;
import com.dong.system.service.ILoginfoService;
import com.dong.system.service.IPermissionService;
import com.dong.system.utils.ActiverUser;
import com.dong.system.utils.ResultObj;
import com.dong.system.utils.TreeNode;
import com.dong.system.utils.TreeNodeBuilder;
import com.dong.system.vo.PermissionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/14 12:35
 * @Version: 1.0
 * @Description: 用户登陆
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    private Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private ILoginfoService loginfoService;

    /**
     * 登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    //登录
    @com.dong.common.aspect.Log(value = "登录操作")
    @RequestMapping("login")
    public ResultObj login(String username, String password, HttpSession session, HttpServletRequest request){
        try{
            Subject subject = SecurityUtils.getSubject();//得到主体
            //封装token身份令牌
            AuthenticationToken token = new UsernamePasswordToken(username,password);
            //主体条用login登陆方法
            subject.login(token);
            ActiverUser activerUser =(ActiverUser)subject.getPrincipal();
            System.out.println(activerUser.getSysUser());
            SysUser sysUser = activerUser.getSysUser();
            System.out.println("sysUser:"+sysUser.toString());
            //写到session
            session.setAttribute("sysUser",sysUser);

            //登陆成功，记录到系统登陆日志
           /* Loginfo loginfo = new Loginfo();
            loginfo.setLoginname(sysUser.getName()+"-"+sysUser.getLoginname());
            loginfo.setLoginip(request.getRemoteAddr());
            loginfo.setLogintime(new Date());
            loginfoService.save(loginfo);*/

            return ResultObj.LOGIN_SUCCESS;
        }catch (Exception e){
            log.error("登陆失败，用户名或密码不正确");
            return ResultObj.LOGIN_ERROR;
        }

    }

    /**
     * 2.登陆成功之后加载首页左侧的导航菜单
     */
    @RequestMapping("loadIndexLeftMenuTreeJson")
    public List<TreeNode> loadIndexLeftMenuTreeJson(PermissionVo permissionVo,HttpSession session){
         //查询所有可用菜单
        permissionVo.setType(Constant.TYPE_MENU);
        permissionVo.setAvailable(Constant.AVAILABLE_TRUE);
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        List<Permission> permissions = null;
        if(sysUser.getType()==Constant.USER_TYPE_SUPER){
            permissions = this.permissionService.queryAllPermissions(permissionVo);
        }else{
            permissions = this.permissionService.queryPermissionByUserIdForList(permissionVo,sysUser.getId());
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission p:permissions) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String href = p.getHref();
            String icon = p.getIcon();
            Boolean spread = p.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(id,pid,title,href,icon,spread));
        }
        return TreeNodeBuilder.build(treeNodes,1);
    }

}
