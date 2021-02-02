package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.constant.Constant;
import com.dong.system.domain.Role;
import com.dong.system.mapper.RoleMapper;
import com.dong.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.DateTimeTools;
import com.dong.system.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-12-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private Log log = LogFactory.getLog(PermissionServiceImpl.class);


    @Transactional(readOnly = true)
    @Override
    public DataGridView loadAllRole(RoleVo roleVo) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if (null != roleVo) {
            qw.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
            qw.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        } else {
            log.info("roleVo为空");
        }
        // qw.orderByDesc("createtime");
        Page<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        this.baseMapper.selectPage(page, qw);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @Override
    public Role addRole(Role role) {
        this.baseMapper.insert(role);
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        this.baseMapper.updateById(role);
        return role;
    }

    @Override
    public void saveRolePermission(Integer roleId, Integer[] pids) {
        //先删除角色-权限中间表数据，然后执行save
        //1.删除原有的数据
        this.baseMapper.deleteRolePermissionByRoleid(roleId);
        //2.持久化数据save
        if(null!=pids && pids.length!=0){
            for (Integer pid:pids) {
                this.baseMapper.saveRolePermission(roleId,pid);
            }
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除角色和权限中间表的数据
        this.getBaseMapper().deleteRolePermissionByRoleid(id);
        this.getBaseMapper().deleteUserRoleByRoleId(id);
        return super.removeById(id);//删除角色;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        //根据角色ID删除角色和权限中间表的数据
        if(idList.size()>0) {
            for (Serializable id : idList) {
                this.getBaseMapper().deleteRolePermissionByRoleid(id);
                this.getBaseMapper().deleteUserRoleByRoleId(id);

            }
        }
        return super.removeByIds(idList);
    }


    @Override
    public DataGridView queryRolesByUserId(Integer userId) {
        QueryWrapper<Role> qw=new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        //查询所有可用的角色
        List<Role> allRoles=this.getBaseMapper().selectList(qw);

        //查询当前用户拥有的角色ID
        List<Integer> roleIds=this.getBaseMapper().selectRoleIdsByUserId(userId);

        List<Role> currentUserRoles=new ArrayList<Role>();
        if(roleIds!=null&&roleIds.size()!=0) {
            qw.in("id", roleIds);
            currentUserRoles=this.getBaseMapper().selectList(qw);
        }

        List<Map<String,Object>> res=new ArrayList<>();

        for (Role r1 : allRoles) {
            Boolean LAY_CHECKED=false;
            for (Role r2 : currentUserRoles) {
                if(r1.getId()==r2.getId()) {
                    LAY_CHECKED=true;
                    break;
                }
            }
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("id", r1.getId());
            map.put("name", r1.getName());
            map.put("remark", r1.getRemark());
            map.put("LAY_CHECKED", LAY_CHECKED);
            map.put("createtime",DateTimeTools.formatDate(r1.getCreatetime(),"yyyy-MM-dd HH:mm:ss"));
            res.add(map);
        }
        return new DataGridView(res);
    }


    @Override
    public List<String> queryRoleNamesByUserId(Integer id) {
        //查询当前用户拥有的角色ID
        List<Integer> roleIds=this.getBaseMapper().selectRoleIdsByUserId(id);
        QueryWrapper<Role> qw=new QueryWrapper<>();
        qw.eq("available", Constant.AVAILABLE_TRUE);
        List<Role> currentUserRoles=new ArrayList<>();
        if(roleIds!=null&&roleIds.size()!=0) {
            qw.in("id", roleIds);
            currentUserRoles = this.getBaseMapper().selectList(qw);
        }
        List<String>  roles=new ArrayList<>();
        for (Role role : currentUserRoles) {
            roles.add(role.getName());
        }
        return roles;
    }



}
