package com.dong.system.controller;


import com.dong.system.constant.Constant;
import com.dong.system.domain.Permission;
import com.dong.system.service.IPermissionService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.utils.TreeNode;
import com.dong.system.vo.PermissionVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM
 * @since 2020-11-14
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private Log log=LogFactory.getLog(MenuController.class);

    @Autowired
    private IPermissionService permissionService;

    /**
     * 1.全查询
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        permissionVo.setType(Constant.TYPE_MENU);//只查询菜单
        return this.permissionService.loadAllPermission(permissionVo);
    }

    /**
     * 2.生成左侧菜单dtree树 Json
     */
    @RequestMapping("loadAllMenuTreeJson")
    public DataGridView loadMenuTreeJson(PermissionVo permissionVo){
        permissionVo.setAvailable(Constant.AVAILABLE_TRUE);//只查可用的
        permissionVo.setType(Constant.TYPE_MENU);
        List<Permission> allMenu=this.permissionService.queryAllPermissions(permissionVo);

        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission p : allMenu) {
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            Boolean spread=p.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }
        return new DataGridView(treeNodes);

    }

    /**
     * 加载菜单最大的排序码
     */
    @RequestMapping("loadPermissionMaxOrderNum")
    public DataGridView loadPermissionMaxOrderNum() {
        Integer maxOrderNum=this.permissionService.queryPermissionMaxOrdernum();
        return new DataGridView(maxOrderNum+1);

    }

    /**
     * 添加
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(Permission permisison) {
        permisison.setType(Constant.TYPE_MENU);
        try {
            permissionService.addPermission(permisison);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }

    }
    /**
     * 修改
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(Permission permisison) {
        try {
            permissionService.updatePermission(permisison);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * 根据菜单ID查询当前菜单有多少子节点
     * @param id
     * @return
     */
    @RequestMapping("checkCurrentMenuHasChild")
    public DataGridView checkCurrentMenuHasChild(Integer id) {
        Integer count=this.permissionService.queryPermissionCountByPid(id);
        return new DataGridView(count);
    }

    /**
     * 删除
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(Integer id) {
        try {
            permissionService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }

}

