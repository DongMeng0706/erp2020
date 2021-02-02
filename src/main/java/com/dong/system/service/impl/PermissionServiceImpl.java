package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.constant.Constant;
import com.dong.system.domain.Permission;
import com.dong.system.mapper.PermissionMapper;
import com.dong.system.mapper.RoleMapper;
import com.dong.system.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.TreeNode;
import com.dong.system.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    private Log log = LogFactory.getLog(PermissionServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Permission> queryAllPermissions(PermissionVo permissionVo) {
        PermissionMapper permissionMapper = this.getBaseMapper();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if(null!=permissionVo){
            queryWrapper.eq(StringUtils.isNoneBlank(permissionVo.getType()),"type",permissionVo.getType());
            queryWrapper.eq(permissionVo.getAvailable()!=null,"available",permissionVo.getAvailable());
            queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        }else{
            log.info("permissionVo的查询参数为空");
        }
        queryWrapper.orderByAsc("ordernum");
        List<Permission> list = permissionMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public DataGridView loadAllPermission(PermissionVo permissionVo) {

        Page<Permission> page=new Page<Permission>(permissionVo.getPage(), permissionVo.getLimit());

        QueryWrapper<Permission> qw=new QueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(permissionVo.getType()), "type", permissionVo.getType());
        qw.eq(permissionVo.getAvailable()!=null, "available", permissionVo.getAvailable());
        qw.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        qw.eq(permissionVo.getId()!=null, "id", permissionVo.getId()).or().eq(permissionVo.getId()!=null, "pid", permissionVo.getId());
        qw.orderByAsc("ordernum");
        this.getBaseMapper().selectPage(page, qw);

        return new DataGridView(page.getTotal(), page.getRecords());

    }

    @Override
    public Integer queryPermissionMaxOrdernum() {
        return this.getBaseMapper().queryPermissionMaxOrderNum();
    }

    @Override
    public Permission addPermission(Permission permission) {
        this.baseMapper.insert(permission);
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {
        this.baseMapper.updateById(permission);
        return permission;
    }

    @Override
    public Integer queryPermissionCountByPid(Integer id) {
        return this.baseMapper.queryPermissionCountByPid(id);
    }

    @Override
    public DataGridView queryRolePermissionByRoleId(Integer roleId) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        queryWrapper.eq("available",Constant.AVAILABLE_TRUE);
        //1.查询所有可用的权限菜单
        List<Permission> allPermissionList = this.baseMapper.selectList(queryWrapper);
        //2.根据角色ID查询当前用户拥有的权限ID集合
        List<Integer> permissionIds = this.baseMapper.queryPermissionIdsByRoleId(roleId);
        //3.定义当前角色的权限菜单集合
        List<Permission> currentRolePermissions = null;
        //4.根据查询到的角色ID，获取当前角色的权限菜单
        if(permissionIds==null || permissionIds.size()==0){
            currentRolePermissions = new ArrayList<>();
        }else{
            queryWrapper.in("id",permissionIds);
            currentRolePermissions = this.baseMapper.selectList(queryWrapper);
        }
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        for (Permission p1: allPermissionList) {
            String checkArr="0";
            for (Permission p2:currentRolePermissions){
                if(p1.getId()==p2.getId()) {
                    checkArr="1";
                    break;
                }
            }
            Boolean spread = p1.getOpen()==null?false:p1.getOpen()==1?true:false;
            nodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }
         return new DataGridView(nodes);
    }

    @Override
    public List<Permission> queryPermissionByUserIdForList(PermissionVo psermissionVo, Integer id) {
        // 根据用户ID查询角色ID
        List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
        if (null == roleIds || roleIds.size() == 0) {
            return null;
        } else {
            // 根据角色ID集合查询权限ID
            List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
            if (null == permissionIds || permissionIds.size() == 0) {
                return null;
            } else {
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type", Constant.TYPE_MENU);
                qw.in("id", permissionIds);
                List<Permission> permsssionObjs = this.getBaseMapper().selectList(qw);
                return permsssionObjs;
            }
        }
    }

    @Override
    public List<String> queryPermissionsByUserId(Integer id) {
        // 根据用户ID查询角色ID
        List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
        if (null == roleIds || roleIds.size() == 0) {
            return null;
        } else {
            // 根据角色ID集合查询权限ID
            List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
            if (null == permissionIds || permissionIds.size() == 0) {
                return null;
            } else {
                QueryWrapper<Permission> qw = new QueryWrapper<>();
                qw.eq("available", Constant.AVAILABLE_TRUE);
                qw.eq("type", Constant.TYPE_PERMISSION);
                qw.in("id", permissionIds);
                List<Permission> permsssionObjs = this.getBaseMapper().selectList(qw);
                // 根据用户ID查询权限
                List<String> permissions = new ArrayList<>();
                for (Permission permission : permsssionObjs) {
                    permissions.add(permission.getPercode());
                }
                return permissions;
            }
        }
    }
}
