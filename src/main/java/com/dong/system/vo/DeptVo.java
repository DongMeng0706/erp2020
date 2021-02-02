package com.dong.system.vo;

import com.dong.system.domain.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/11/16 9:25
 * @Version: 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept implements Serializable {
    private static final Long serialVersionUID=1L;

    /**
     * 分页参数
     */
    private Integer page=1;
    private Integer limit=10;
}
