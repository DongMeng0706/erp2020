package com.dong.system.vo;

import com.dong.system.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/12/9 16:55
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysUserVo extends SysUser {

    private static final  long serialVersionUID = 1L;

    /**
     * 分页参数
     */
    private Integer page=1;
    private Integer limit=10;
}
