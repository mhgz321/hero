package com.keaizhale.file.message;

import com.keaizhale.hero.common.message.ServiceErrorMeta;

/**
 * description: FileErrorEnum
 * date: 2023/3/27 10:54
 * author: keaizhale
 * version: 1.0
 */
public enum FileErrorEnum {
    /** 名称不能为空 */
    NAME_NUMM_ERROR("1001"),
    ;
    private ServiceErrorMeta errorMeta;

    FileErrorEnum(String code) {
        this.errorMeta = new ServiceErrorMeta("file.error_", code);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}
