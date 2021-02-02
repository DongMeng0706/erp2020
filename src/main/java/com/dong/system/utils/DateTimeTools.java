package com.dong.system.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: DM
 * @Contact: 256350962@qq.com
 * @Date: 2020/12/25 15:56
 * @Version: 1.0
 * @Description:
 */
public class DateTimeTools {

    /**
     * 格式化时间
     * @param date
     * @param formteType
     * @return
     */
    public static String formatDate(Date date, String formteType){
        SimpleDateFormat sdf = new SimpleDateFormat(formteType);
        if(date==null){
            return "";
        }else{
            return sdf.format(date);
        }
    }
}
