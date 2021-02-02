package com.dong.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dong.system.domain.Notice;
import com.dong.system.mapper.SysNoticeMapper;
import com.dong.system.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<SysNoticeMapper, Notice> implements INoticeService {

    private Log log = LogFactory.getLog(NoticeServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public DataGridView loadAllNoticeList(NoticeVo noticeVo) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>();
        if(null!=noticeVo){
            queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
            queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
            queryWrapper.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        }else{
            log.info("noticeVo查询参数为空");
        }
        queryWrapper.orderByDesc("createtime");
        Page<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        this.baseMapper.selectPage(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    @Override
    public void addNotice(Notice notice) {
        this.baseMapper.insert(notice);
    }

    @Override
    public void updateNotice(Notice notice) {
        this.baseMapper.updateById(notice);
    }
}
