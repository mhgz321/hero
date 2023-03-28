package com.keaizhale.hero.common.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * description: ServiceErrorFactory 错误信息构建工厂
 * date: 2023/3/23 9:37
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public class ServiceErrorFactory {
    private ServiceErrorFactory(){}
    public ServiceErrorFactory(List<String> isvModules){
        HashSet<String> baseNamesSet = new HashSet<>();
        baseNamesSet.add(I18N_OPEN_ERROR);

        if (!isvModules.isEmpty()) {
            baseNamesSet.addAll(isvModules);
        }

        String[] totalBaseNames = baseNamesSet.toArray(new String[0]);

        log.info("加载错误码国际化资源：{}", StringUtils.arrayToCommaDelimitedString(totalBaseNames));
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasenames(totalBaseNames);
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
        setErrorMessageSourceAccessor(messageSourceAccessor);
    }

    public static final String SYS_ERR = "系统错误";

    private static final String I18N_OPEN_ERROR = "i18n/common/error";

    private static Set<String> noModuleCache = new HashSet<>();

    private static Map<String, ServiceError> errorCache = new HashMap<>(64);

    /**
     * 错误信息的国际化信息
     */
    private static MessageSourceAccessor errorMessageSourceAccessor;


    /**
     * 设置国际化资源信息
     */
//    public static void initMessageSource(List<String> isvModules) {
//        HashSet<String> baseNamesSet = new HashSet<>();
//        baseNamesSet.add(I18N_OPEN_ERROR);
//
//        if (!isvModules.isEmpty()) {
//            baseNamesSet.addAll(isvModules);
//        }
//
//        String[] totalBaseNames = baseNamesSet.toArray(new String[0]);
//
//        log.info("加载错误码国际化资源：{}", StringUtils.arrayToCommaDelimitedString(totalBaseNames));
//        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
//        bundleMessageSource.setBasenames(totalBaseNames);
//        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(bundleMessageSource);
//        setErrorMessageSourceAccessor(messageSourceAccessor);
//    }

    /**
     * @title: 构建错误消息
     * @description: 通过ErrorMeta，Locale，params构建国际化错误消息
     * @author: keaizhale
     * @dateTime: 2023/3/23 9:43
     * @param errorMeta 错误信息
     * @param locale 本地化
     * @param params 参数]
     * @return com.keaizhale.hero.common.message.ServiceError 如果没有配置国际化消息，则直接返回errorMeta中的信息
     */
    public static ServiceError getError(ServiceErrorMeta errorMeta, Locale locale, Object... params) {
        String key = errorMeta.getModulePrefix()  + errorMeta.getCode() + locale.toString();
        ServiceError error = errorCache.get(key);
        if (error == null) {
            Assert.notNull(locale, "未设置Locale");
            String modulePrefix = errorMeta.getModulePrefix();
            String code = errorMeta.getCode();
            String msg = getErrorMessage(modulePrefix + code, locale, params);
            if (StringUtils.isEmpty(msg)) {
                msg = SYS_ERR;
            }
            error = new ServiceErrorImpl(code, msg);
            if (params == null || params.length == 0) {
                //当参数不空时，增加缓存
                errorCache.put(key, error);
            }
        }
        return error;
    }


    public static void setErrorMessageSourceAccessor(MessageSourceAccessor errorMessageSourceAccessor) {
        ServiceErrorFactory.errorMessageSourceAccessor = errorMessageSourceAccessor;
    }

    /**
     * @title: 返回本地化信息
     * @description: 获取本地化消息，优化从缓存获取
     * @author: keaizhale
     * @dateTime: 2023/3/23 9:42
     * @param module 错误模块
     * @param locale 本地化,
     * @param params 参数
     * @return java.lang.String
     */
    public static String getErrorMessage(String module, Locale locale, Object... params) {
        if (noModuleCache.contains(module)) {
            log.debug("国际化消息：{}，未配置", module);
            return null;
        }
        try {
            return errorMessageSourceAccessor.getMessage(module, params, locale);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            noModuleCache.add(module);
            return null;
        }
    }
}
