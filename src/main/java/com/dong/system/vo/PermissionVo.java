package com.dong.system.vo;

import com.dong.system.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/14 13:30
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PermissionVo extends Permission {

    private static final  long serialVersionUID = 1L;

    /**
     * 分页参数
     */
    private Integer page=1;
    private Integer limit=10;
}
