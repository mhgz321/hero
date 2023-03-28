package com.keaizhale.hero.common.exception;

import com.keaizhale.hero.common.message.ServiceError;
import com.keaizhale.hero.common.message.ServiceErrorEnum;
import com.keaizhale.hero.common.message.ServiceErrorImpl;

/**
 * description: 统一业务异常
 * date: 2023/3/23 9:28
 * author: keaizhale
 * version: 1.0
 */
public class ServiceException extends RuntimeException {
    private final transient ServiceError error;

    public ServiceException(String code, String msg) {
        super(msg);
        this.error = new ServiceErrorImpl(code, msg);
    }

    public ServiceException(String msg) {
        super(msg);
        String code = ServiceErrorEnum.API_COMMON_ERROR.getErrorMeta().getError().code();
        this.error = new ServiceErrorImpl(code, msg);
    }

    public ServiceException(ServiceError error) {
        super(error.toString());
        this.error = error;
    }

    public ServiceError getError() {
        return error;
    }
}
