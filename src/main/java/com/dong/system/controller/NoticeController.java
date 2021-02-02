package com.dong.system.controller;


import com.dong.system.domain.Notice;
import com.dong.system.domain.SysUser;
import com.dong.system.service.INoticeService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.vo.NoticeVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private Log log = LogFactory.getLog(NoticeController.class);

    @Autowired
    private INoticeService noticeService;

    /**
     * 1.查询全部通知公告信息
     */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNoticeList(NoticeVo noticeVo){
        return this.noticeService.loadAllNoticeList(noticeVo);
    }

    /**
     * 2.新增通知公告
     */
    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice, HttpSession session){
        SysUser sysUser = (SysUser) session.getAttribute("sysUser");
        notice.setOpername(sysUser.getName());
        notice.setCreatetime(new Date());
        try{
            noticeService.addNotice(notice);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            log.info("添加失败");
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 3.修改通知公告
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try{
            noticeService.updateNotice(notice);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            log.info("修改失败");
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 4.批量删除
     */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(Integer[] ids){
        try {
            if(ids==null||ids.length==0) {
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for (Integer integer : ids) {
                idList.add(integer);
            }
            noticeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 5.单个删除
     * @param id
     * @return
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id) {
        try {
            noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 6.根据ID查询通知公告信息
     */
    @RequestMapping("loadNoticeById")
    public DataGridView loadNoticeById(Integer id){
        return new DataGridView(this.noticeService.getById(id));
    }
}

