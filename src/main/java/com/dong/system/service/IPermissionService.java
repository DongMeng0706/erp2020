package com.dong.system.service;

import com.dong.system.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM
 * @since 2020-11-14
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 1.查询全部菜单或权限
     */
    public List<Permission> queryAllPermissions(PermissionVo permissionVo);

    /**
     * 2.查询全部Menu包括菜单和权限，返回封装DatagridView
     */
    public DataGridView loadAllPermission(PermissionVo permissionVo);

    /**
     * 3.查询权限最大的排序码
     */
    public Integer queryPermissionMaxOrdernum();

    /**
     * 4.新增
     */
    public Permission addPermission(Permission permission);

    /**
     * 5.修改
     */
    public Permission updatePermission(Permission permission);

    /**
     * 6.查询子栏目数量
     */
    public Integer queryPermissionCountByPid(Integer id);

    /**
     * 7.根据角色ID查询所有菜单权限集合
     */
    public DataGridView queryRolePermissionByRoleId(Integer roleId);

    /**
     * 8.根据用户ID查询菜单集合
     */
    List<Permission> queryPermissionByUserIdForList(PermissionVo permissionVo,Integer id);

    List<String> queryPermissionsByUserId(Integer userId);
}
