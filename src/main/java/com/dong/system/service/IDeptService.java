package com.dong.system.service;

import com.dong.system.domain.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.DeptVo;

import java.util.List;

/**
 * <p>
 *  部门业务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 1.查询全部部门并封装DataGridView
     */
    public DataGridView loadAllDept(DeptVo deptVo);

    /**
     * 2.查询全部部门集合
     */
    public List<Dept> queryAllDeptForList(DeptVo deptVo);

    /**
     * 3.添加
     */
    public Dept addDept(Dept dept);

    /**
     * 4.修改
     */
    public Dept updateDept(Dept dept);

    /**
     * 5.加载部门最大的排序码
     */
    public Integer queryDeptMaxOrderNum();

    /**
     * 6.查询当前部门下的子部门数量
     */
    public Integer queryDeptCountByPid(Integer id);
}

