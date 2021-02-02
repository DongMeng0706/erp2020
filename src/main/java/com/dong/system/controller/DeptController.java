package com.dong.system.controller;


import com.dong.system.constant.Constant;
import com.dong.system.domain.Dept;
import com.dong.system.service.IDeptService;
import com.dong.system.utils.DataGridView;
import com.dong.system.utils.ResultObj;
import com.dong.system.utils.TreeNode;
import com.dong.system.vo.DeptVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DM
 * @since 2020-11-15
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    private Log log = LogFactory.getLog(DeptController.class);

    @Autowired
    private IDeptService deptService;

    /**
     * 全查询
     */
    @com.dong.common.aspect.Log("查看部门信息")
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo) {
        return this.deptService.loadAllDept(deptVo);
    }

    /**
     * 生成json树
     */
    @com.dong.common.aspect.Log("加载部门管理左侧的列表树")
    @RequestMapping("loadAllDeptTreeJson")
    public DataGridView loadAllDeptTreeJson(DeptVo deptVo) {
        deptVo.setAvailable(Constant.AVAILABLE_TRUE);//只查可用的
        List<Dept> allDept=this.deptService.queryAllDeptForList(deptVo);

        List<TreeNode> treeNodes=new ArrayList<>();
        for (Dept dept : allDept) {
            Integer id=dept.getId();
            Integer pid=dept.getPid();
            String title=dept.getTitle();
            Boolean spread=dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(id, pid, title, spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载部门最大的排序码
     */
    @com.dong.common.aspect.Log("加载部门最大的排序码")
    @RequestMapping("loadDeptMaxOrderNum")
    public DataGridView loadDeptMaxOrderNum() {
        Integer maxOrderNum=this.deptService.queryDeptMaxOrderNum();
        return new DataGridView(maxOrderNum+1);

    }

    /**
     * 添加
     */
    @RequestMapping("addDept")
    public ResultObj addDept(Dept dept, HttpSession session) {
        dept.setCreatetime(new Date());//设置时间
        try {
            deptService.addDept(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            log.error("添加失败");
            return ResultObj.ADD_ERROR;
        }

    }
    /**
     * 修改
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(Dept dept) {
        try {
            deptService.updateDept(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error("修改失败");
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 查询当前部门下的子节点数量：查询当前部门的下级部门数量
     */
    @RequestMapping("checkCurrentDeptHasChild")
    public DataGridView checkCurrentDeptHasChild(Integer id){
        Integer count = this.deptService.queryDeptCountByPid(id);
        return  new DataGridView(count);
    }

    /**
     * 删除
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(Integer id){
        try{
            deptService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            log.error("删除失败");
            return ResultObj.DELETE_ERROR;
        }
    }


}

