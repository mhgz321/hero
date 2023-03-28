package com.keaizhale.hero.common.message;

import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * description: ServiceErrorImpl
 * date: 2023/3/23 9:40
 * author: keaizhale
 * version: 1.0
 */
@Setter
@AllArgsConstructor
public class ServiceErrorImpl implements ServiceError {
    private String code;
    private String msg;

    @Override
    public String code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
