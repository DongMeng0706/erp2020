package com.dong.system.service;

import com.dong.system.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.system.utils.DataGridView;
import com.dong.system.vo.NoticeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
public interface INoticeService extends IService<Notice> {

    /**
     * 1.查询全部通知公告
     */
    public DataGridView loadAllNoticeList(NoticeVo noticeVo);

    /**
     * 2.新增
     */
    public void addNotice(Notice notice);

    /**
     * 3.修改
     */
    public void updateNotice(Notice notice);

}
