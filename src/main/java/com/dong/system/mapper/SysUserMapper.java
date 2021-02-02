package com.dong.system.mapper;

import com.dong.system.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DM
 * @since 2020-11-12
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     *
     */
    Integer queryUserMaxOrderNum();

    /**
     * 根据用户ID删除用户和角色的关联关系
     */
    void deleteRoleUserByUserId(Serializable id);

    /**
     * 根据角色ID用户角色关联关系
     */
    void deleteRoleUserByRoleId(Serializable id);

    /**
     * 保存 用户-角色 中间表数据
     */
    void saveUserRole(@Param("uid") Integer userId, @Param("rid") Integer rid);
}
