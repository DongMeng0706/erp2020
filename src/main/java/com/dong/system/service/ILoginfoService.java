package com.dong.system.service;

import com.dong.system.domain.Loginfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.LoginfoVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
public interface ILoginfoService extends IService<Loginfo> {

    /**
     * 1.查询全部系统日志
     */
    public DataGridView loadAllLogInfoList(LoginfoVo loginfoVo);

}
