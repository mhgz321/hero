package com.keaizhale.hero.common.bean;

/**
 * description: 系统通用常量
 * date: 2023/3/24 9:05
 * author: keaizhale
 * version: 1.0
 */
public interface CommonConstant {
    /** token请求头名称. */
    String TOKEN_HEADER = "Authorization";

    /** 用户token字段. */
    String ACCESS_TOKEN = "access_token";

    /** token前缀. */
    String BEARER_TYPE = "Bearer";

    /** 租户ID. */
    String TENANT_ID_PARAM = "tenantId";

    /** 缓存的requestKey. */
    String REQUEST = "x-hero-request";

    /** 请求头中上送的国际化标识zh_CN、en. */
    String locale = "locale";


}
