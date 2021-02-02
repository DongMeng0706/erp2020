package com.dong.system.controller;


import com.dong.system.service.ILoginfoService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.vo.LoginfoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@RestController
@RequestMapping("/loginfo")
public class SysLoginfoController {

    private Log log=LogFactory.getLog(SysLoginfoController.class);

    @Autowired
    private ILoginfoService loginfoService;

    /**
     *  查询系统日志信息
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView laodAllLoginfo(LoginfoVo loginfoVo){
        return loginfoService.loadAllLogInfoList(loginfoVo);
    }

    /**
     * 删除
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id) {
        try {
            loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(Integer[] ids) {
        try {
            if(ids==null||ids.length==0) {
                log.error("参数不能为空");
                return ResultObj.DELETE_ERROR;
            }
            Collection<Serializable> idList=new ArrayList<Serializable>();
            for (Integer integer : ids) {
                idList.add(integer);
            }
            loginfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }

    }


}

