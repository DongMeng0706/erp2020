package com.dong.system.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dong.system.constant.Constant;
import com.dong.system.domain.Permission;
import com.dong.system.service.IPermissionService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.vo.PermissionVo;

/**
 * <p>
 *  权限前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

	
	private Log log=LogFactory.getLog(PermissionController.class);
	
	@Autowired
	private IPermissionService permissionService;
	
	
	/**
	 * 全查询
	 */
	@RequestMapping("loadAllPermission")
	public DataGridView loadAllPermission(PermissionVo permissionVo) {
		permissionVo.setType(Constant.TYPE_PERMISSION);//只查权限
		return this.permissionService.loadAllPermission(permissionVo);
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
	@RequestMapping("addPermission")
	public ResultObj addPermission(Permission permisison) {
		permisison.setType(Constant.TYPE_PERMISSION);
		permisison.setOpen(0);
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
	@RequestMapping("updatePermission")
	public ResultObj updatePermission(Permission permisison) {
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
	@RequestMapping("checkCurrentPermissionHasChild")
	public DataGridView checkCurrentPermissionHasChild(Integer id) {
		Integer count=this.permissionService.queryPermissionCountByPid(id);
		return new DataGridView(count);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("deletePermission")
	public ResultObj deletePermission(Integer id) {
		try {
			permissionService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
		
	}

}

