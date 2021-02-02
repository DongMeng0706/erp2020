package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.domain.Dept;
import com.dong.system.mapper.DeptMapper;
import com.dong.system.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    private Log log = LogFactory.getLog(DeptServiceImpl.class);

    @Override
    public DataGridView loadAllDept(DeptVo deptVo) {
        DeptMapper deptMapper =  this.getBaseMapper();
        Page<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null,"id",deptVo.getId()).or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        queryWrapper.orderByAsc("ordernum");
        deptMapper.selectPage(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public List<Dept> queryAllDeptForList(DeptVo deptVo) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
        queryWrapper.eq(deptVo.getAvailable()!=null,"available",deptVo.getAvailable());
        return this.getBaseMapper().selectList(queryWrapper);
    }

    @Override
    public Dept addDept(Dept dept) {
        this.getBaseMapper().insert(dept);
        return dept;
    }

    @Override
    public Dept updateDept(Dept dept) {
        this.baseMapper.updateById(dept);
        return dept;
    }

    @Override
    public Integer queryDeptMaxOrderNum() {
        return this.getBaseMapper().queryDeptMaxOrderNum();
    }

    @Override
    public Integer queryDeptCountByPid(Integer id) {
        return this.getBaseMapper().queryDeptCountByPid(id);
    }


}
