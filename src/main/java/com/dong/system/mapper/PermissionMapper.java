package com.dong.system.mapper;

import com.dong.system.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DM
 * @since 2020-11-14
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    public Integer queryPermissionMaxOrderNum();

    public Integer queryPermissionCountByPid(Integer id);

    /**
     * 根据角色ID查询当前角色拥有的权限ID集合
     * @param roleId
     * @return
     */
    List<Integer> queryPermissionIdsByRoleId(@Param("roleId")Integer roleId);

    /**
     * 根据角色ID集合查询菜单和权限ID
     * @param roleIds
     * @return
     */
    List<Integer> queryPermissionIdsByRoleIds(@Param("roleIds")List<Integer> roleIds);
}
