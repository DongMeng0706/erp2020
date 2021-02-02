package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.constant.Constant;
import com.dong.system.domain.Dept;
import com.dong.system.domain.SysUser;
import com.dong.system.mapper.RoleMapper;
import com.dong.system.mapper.SysUserMapper;
import com.dong.system.service.IDeptService;
import com.dong.system.service.IRoleService;
import com.dong.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.MD5Utils;
import com.dong.system.vo.SysUserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-12
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    @Lazy
    private IDeptService deptService;

    @Autowired
    private RoleMapper roleMapper;
    /**
     * 声明日志输入对象
     */
    private Log Log = LogFactory.getLog(SysUserServiceImpl.class);

    @Override
    public SysUser queryUserByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if(null==userName){
            log.error("登陆用户名不得为空");
            throw  new RuntimeException("登陆用户名不得为空");
        }
        queryWrapper.eq("loginname",userName);
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper);
        return sysUser;
    }

    @Override
    public DataGridView loadAllUser(SysUserVo sysUserVo) {
        Page<SysUser> page=new Page<>(sysUserVo.getPage(), sysUserVo.getLimit());
        QueryWrapper<SysUser> qw=new QueryWrapper<>();
        qw.eq("type", Constant.USER_TYPE_NORMAL);
        this.getBaseMapper().selectPage(page, qw);
        List<SysUser> list = page.getRecords();
        for (SysUser user : list) {
            Integer deptid=user.getDeptid();
            Integer mgr=user.getMgr();
            //根据ID去查询部门
            Dept dept = deptService.getById(deptid);
            user.setDeptname(dept.getTitle());

            //根据领导ID去查询领导名称
            if(null!=mgr) {//如果直接使用this那么缓存切面不生效
                SysUser user2 =this.baseMapper.selectById(mgr);
                user.setLeadername(user2.getName());
            }
        }
        return new DataGridView(page.getTotal(), list);

    }

    @Override
    public List<SysUser> queryUserByDeptId(Integer deptid) {
        if(null==deptid){
            return  null;
        }else{
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
            queryWrapper.eq("type",Constant.USER_TYPE_NORMAL);
            queryWrapper.eq(deptid!=null,"deptid",deptid);
            return this.baseMapper.selectList(queryWrapper);
        }
    }

    @Override
    public Integer loadMaxOrderNum() {
        return this.baseMapper.queryUserMaxOrderNum();
    }

    @Override
    public SysUser addUser(SysUser sysUser) {
        this.baseMapper.insert(sysUser);
        return sysUser;
    }

    @Override
    public SysUser updateUser(SysUser sysUser) {
        this.baseMapper.updateById(sysUser);
        return sysUser;
    }

    @Override
    public void resetUserPwd(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setSalt(MD5Utils.getSalt());
        sysUser.setPwd(MD5Utils.md5(Constant.USER_DEFAULT_PWD,sysUser.getSalt(),2));
        this.baseMapper.updateById(sysUser);
    }

    @Override
    public void saveUserRole(Integer userId, Integer[] rids) {
        this.roleMapper.deleteUserRoleByUserId(userId);
        if(rids!=null && rids.length>0){
            for (Integer rid:rids) {
                this.baseMapper.saveUserRole(userId,rid);
            }
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        roleMapper.deleteUserRoleByUserId(id);
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable id:idList) {
            this.roleMapper.deleteUserRoleByUserId(id);
        }
        return super.removeByIds(idList);
    }
}
