package com.dong.system.controller;


import com.dong.system.domain.Role;
import com.dong.system.service.IPermissionService;
import com.dong.system.service.IRoleService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.vo.RoleVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    private Log log=LogFactory.getLog(RoleController.class);


    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    /**
     * 全查询
     * @param roleVo
     * @return
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo) {
        return this.roleService.loadAllRole(roleVo);
    }


    /**
     * 添加
     */
    @RequestMapping("addRole")
    public ResultObj addRole(Role role) {
        role.setCreatetime(new Date());//设置时间
        try {
            roleService.addRole(role);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }

    }
    /**
     * 修改
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(Role role) {
        try {
            roleService.updateRole(role);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }

    }
    /**
     * 删除
     */
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id) {
        try {
            roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteRole")
    public ResultObj batchDeleteRole(Integer[] ids) {
        try {
            if(ids==null||ids.length==0) {
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for (Integer integer : ids) {
                idList.add(integer);
            }
            roleService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }


    /**
     * 根据角色ID加载并选中权限、菜单
     */
    @RequestMapping("loadRolePermission")
    public DataGridView loadRolePermission(@RequestParam("id")Integer roleId){
        return this.permissionService.queryRolePermissionByRoleId(roleId);
    }

    /**
     * 保存角色-权限的关联关系
     */
    @RequestMapping("saveRolePermission")
    public  ResultObj saveRolePermission(Integer roleId,Integer [] pids){
        try{
            this.roleService.saveRolePermission(roleId,pids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            log.error("分配失败");
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * 根据用户ID加载用户管理页面的分配角色弹出层里面的数据  并选中之前拥有的角色
     */
    @RequestMapping("loadRolesByUserId")
    public DataGridView loadRolesByUserId(@RequestParam("userId")Integer userId) {
        return this.roleService.queryRolesByUserId(userId);
    }
}

