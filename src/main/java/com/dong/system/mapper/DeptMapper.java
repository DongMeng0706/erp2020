package com.dong.system.mapper;

import com.dong.system.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
public interface DeptMapper extends BaseMapper<Dept> {

    Integer queryDeptMaxOrderNum();

    Integer queryDeptCountByPid(Integer id);
}
