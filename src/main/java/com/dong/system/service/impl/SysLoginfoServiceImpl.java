package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.domain.Loginfo;
import com.dong.system.mapper.LoginfoMapper;
import com.dong.system.service.ILoginfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@Service
public class SysLoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements ILoginfoService {

    private Log log = LogFactory.getLog(SysLoginfoServiceImpl.class);


    @Override
    public DataGridView loadAllLogInfoList(LoginfoVo loginfoVo) {
        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<Loginfo>();
        if(null!=loginfoVo){
            queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()), "loginname", loginfoVo.getLoginname());
            queryWrapper.ge(loginfoVo.getStartTime()!=null, "logintime", loginfoVo.getStartTime());
            queryWrapper.le(loginfoVo.getEndTime()!=null, "logintime", loginfoVo.getEndTime());
        }else{
            log.info("loginfoVO参数为空");
        }
        queryWrapper.orderByDesc("logintime");
        Page<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        this.baseMapper.selectPage(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
