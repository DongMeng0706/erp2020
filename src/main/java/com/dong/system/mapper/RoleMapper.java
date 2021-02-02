package com.dong.system.mapper;

import com.dong.system.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DM
 * @since 2020-12-09
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 1.根据角色ID删除角色-权限的中间关联表的数据（sys-role-permission）
     * @param id
     */
    public void deleteRolePermissionByRoleid(Serializable id);

    /**
     * 2.根据菜单权限ID 删除 角色-权限中间表数据 sys-role-permission
     * @param id
     */
    public void deleteRolePermissionByPermissionid(Serializable id);


    /**
     * 3.保存角色-菜单 关系--持久化sys-role-permission 中间表数据
     */
    public void saveRolePermission(@Param("rid") Integer roleId, @Param("pid") Integer pid);

    /**
     * 4.根据用户ID 删除 用户-角色 中间关联表数据
     */
    public void deleteUserRoleByUserId(Serializable id);

    /**
     * 5.根据角色ID 删除 用户-角色  中间关联表数据
     */
    public void deleteUserRoleByRoleId(Serializable id);

    /**
     * 6. 根据用户ID 查询所有的角色ID
     */
    public List<Integer> selectRoleIdsByUserId(Integer userId);
}
