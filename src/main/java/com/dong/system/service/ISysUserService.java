package com.dong.system.service;

import com.dong.system.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.SysUserVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM
 * @since 2020-11-12
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 1.根据登陆名查询用户对象
     */
    public SysUser queryUserByUserName(String userName);

    /**
     * 2.查询全部用户集合
     */
    public DataGridView loadAllUser(SysUserVo sysUserVo);

    /**
     * 3.根据部门ID查询员工集合
     */
    public List<SysUser> queryUserByDeptId(Integer deptid);

    /**
     * 4.加载最大排序码
     */
    public Integer loadMaxOrderNum();

    public SysUser addUser(SysUser sysUser);

    public SysUser updateUser(SysUser sysUser);

    /**
     * 7.重置密码
     */
    public void resetUserPwd(Integer id);

    /**
     * 8.给用户分配角色权限
     */
    public void saveUserRole(Integer userId,Integer[] rids);

}
