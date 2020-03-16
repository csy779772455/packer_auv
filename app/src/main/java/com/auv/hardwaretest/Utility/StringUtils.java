package com.auv.hardwaretest.Utility;

/**
 * @author LiChuang
 * @version 1.0
 * @ClassName StringUtils
 * @Description TODO 类描述
 * @since 2020/2/6 11:10
 **/
public class StringUtils {


    static boolean isEmpty(String value){
        return  !isNotEmpty(value);
    }

    public static boolean isNotEmpty(String value){
        return (null!=value && !"".equals(value) && value.length()>0);
    }

}
