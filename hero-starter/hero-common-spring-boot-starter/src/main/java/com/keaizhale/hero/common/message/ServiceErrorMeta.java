package com.keaizhale.hero.common.message;

import com.keaizhale.hero.common.context.ServiceContext;
import com.keaizhale.hero.common.exception.ServiceException;
import lombok.Getter;

import java.util.Locale;

/**
 * description: ServiceErrorMeta
 * date: 2023/3/23 9:34
 * author: keaizhale
 * version: 1.0
 */
@Getter
public class ServiceErrorMeta {
    private String modulePrefix;
    private String code;

    public ServiceErrorMeta(String modulePrefix, String code) {
        this.modulePrefix = modulePrefix;
        this.code = code;
    }

    public ServiceError getError() {
        //TODO 暂时全局设置中文
        return ServiceErrorFactory.getError(this, ServiceContext.getCurrentContext().getLocale());
    }

    public ServiceException getException(Object... params) {
        //TODO 暂时全局设置中文
        ServiceError error = ServiceErrorFactory.getError(this, ServiceContext.getCurrentContext().getLocale(), params);
        return new ServiceException(error);
    }
}
