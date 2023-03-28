package com.keaizhale.hero.common.message;

/**
 * description: 异常定义
 * date: 2023/3/23 9:32
 * author: keaizhale
 * version: 1.0
 */
public interface ServiceError {
    /*
     * @title: code
     * @description: 异常码
     * @author: keaizhale
     * @dateTime: 2023/3/23 9:32
     */
    String code();

    /*
     * @title: msg
     * @description: 异常描述
     * @author: keaizhale
     * @dateTime: 2023/3/23 9:33
     */
    String msg();
}
