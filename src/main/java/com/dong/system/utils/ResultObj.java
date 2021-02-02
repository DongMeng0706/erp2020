package com.dong.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/12 15:25
 * @Version: 1.0
 * @Description:状态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    public static ResultObj LOGIN_SUCCESS=new ResultObj("登陆成功");
    public static ResultObj LOGIN_ERROR=new ResultObj(-1,"登陆失败,用户名或密码不正确");

    public static ResultObj ADD_SUCCESS=new ResultObj("添加成功");
    public static ResultObj ADD_ERROR=new ResultObj(-1,"添加失败");

    public static ResultObj UPDATE_SUCCESS=new ResultObj("修改成功");
    public static ResultObj UPDATE_ERROR=new ResultObj(-1,"修改失败");

    public static ResultObj DELETE_SUCCESS=new ResultObj("删除成功");
    public static ResultObj DELETE_ERROR=new ResultObj(-1,"删除失败");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj("分配成功");
    public static final ResultObj DISPATCH_ERROR = new ResultObj("分配失败");

    public static final ResultObj RESET_SUCCESS = new ResultObj("重置成功");
    public static final ResultObj RESET_ERROR = new ResultObj("重置失败");


    private Integer code=200;
    private String msg="";

    public ResultObj(String msg) {
        super();
        this.msg = msg;
    }

}
