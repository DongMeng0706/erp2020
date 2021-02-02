package com.dong.system.service;

import com.dong.system.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM
 * @since 2020-12-09
 */
public interface IRoleService extends IService<Role> {

    /**
     * 查询所有角色
     * @param roleVo
     * @return
     */
    public DataGridView loadAllRole(RoleVo roleVo);

    public Role addRole(Role role);

    public Role updateRole(Role role);

    public void saveRolePermission(Integer roleId,Integer[] pids);

    /**
     * 根据用户ID 拥有的角色
     */
    public DataGridView queryRolesByUserId(Integer userId);

    /**
     * 根据用户ID查询用户拥有的角色名称
     */
    public List<String> queryRoleNamesByUserId(Integer id);
}
