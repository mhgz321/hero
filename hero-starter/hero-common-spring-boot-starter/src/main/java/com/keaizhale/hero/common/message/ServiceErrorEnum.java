package com.keaizhale.hero.common.message;

/**
 * description: 异常枚举定义
 * date: 2023/3/23 9:32
 * author: keaizhale
 * version: 1.0
 */
public enum ServiceErrorEnum {
    /** 未知错误 */
    API_UNKNOWN_ERROR("9999"),
    /** 通用错误 */
    API_COMMON_ERROR("9001"),

    /** 参数错误 */
    API_PARAMS_ERROR("9002"),
    ;
    private ServiceErrorMeta errorMeta;

    ServiceErrorEnum(String code) {
        this.errorMeta = new ServiceErrorMeta("common.error_", code);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}
