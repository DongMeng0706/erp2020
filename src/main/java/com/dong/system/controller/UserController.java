package com.dong.system.controller;

import com.dong.system.constant.Constant;
import com.dong.system.domain.SysUser;
import com.dong.system.service.ISysUserService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.MD5Utils;
import com.dong.system.utils.ResultObj;
import com.dong.system.vo.SysUserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/12/9 17:03
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Log log = LogFactory.getLog(DeptController.class);

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询全部
     */
    @com.dong.common.aspect.Log("查询全部用户")
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(SysUserVo sysUserVo){
        return  this.sysUserService.loadAllUser(sysUserVo);
    }

    /**
     * 根据部门ID查询员工
     */
    @RequestMapping("queryUserByDeptId")
    public DataGridView queryUserByDeptId(Integer deptid) {
        return new DataGridView(this.sysUserService.queryUserByDeptId(deptid));
    }

    /**
     * 加载最大排序码
     */
    @RequestMapping("loadUserMaxOrderNum")
    public DataGridView loadUserMaxOrderNum() {
        Integer max=this.sysUserService.loadMaxOrderNum();
        return new DataGridView(max+1);
    }

    /**
     * 添加用户
     */
    @RequestMapping("addUser")
    public ResultObj addUser(SysUser user) {
        user.setHiredate(new Date());//设置入职时间
        user.setType(Constant.USER_TYPE_NORMAL);//用户类型
        user.setImgpath(Constant.USER_DEFAULT_IMAGE);
        user.setSalt(MD5Utils.getSalt());
        user.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD, user.getSalt(), 2));
        try {
            sysUserService.addUser(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }

    }


    /**
     * 根据用户ID查询用户对象
     */
    @RequestMapping("loadUserByUserId")
    public DataGridView loadUserByUserId(Integer userId) {
        SysUser user=this.sysUserService.getById(userId);
        return new DataGridView(user);
    }
    /**
     * 修改
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(SysUser user) {
        try {
            sysUserService.updateUser(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * 删除
     */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id) {
        try {
            sysUserService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteUser")
    public ResultObj batchDeleteUser(Integer[] ids) {
        try {
            if(ids==null||ids.length==0) {
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for (Integer integer : ids) {
                idList.add(integer);
            }
            sysUserService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 重置密码
     */
    @RequestMapping("resetUserPwd")
    public ResultObj resetUserPwd(Integer id) {
        try {
            this.sysUserService.resetUserPwd(id);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            log.error("重置失败");
            return ResultObj.RESET_ERROR;
        }

    }

    /**
     * 分配用户角色
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer userId,Integer [] rids){
        try{
            this.sysUserService.saveUserRole(userId,rids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            log.error("分配失败");
            return ResultObj.DISPATCH_ERROR;
        }
    }

}
