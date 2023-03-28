package com.keaizhale.hero.common.context;

import com.keaizhale.hero.common.bean.CommonConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description: ServiceContext
 * date: 2023/3/24 9:20
 * author: keaizhale
 * version: 1.0
 */
public class ServiceContext extends ConcurrentHashMap<String, Object> {
    protected static Class<? extends ServiceContext> contextClass = ServiceContext.class;

    protected static final ThreadLocal<? extends ServiceContext> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return contextClass.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    });

    public ServiceContext setTenantId(String tenantId) {
        set(CommonConstant.TENANT_ID_PARAM, tenantId);
        return this;
    }

    /**
     * 设置请求的request
     *
     * @param request
     */
    public ServiceContext setRequest(HttpServletRequest request) {
        set(CommonConstant.REQUEST, request);
        return this;
    }

    public Locale getLocale() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        //先看请求参数中是否上送了国际化字段
        String locale = request.getHeader(CommonConstant.locale);
        if (StringUtils.hasLength(locale)) {
            Locale[] locales = Locale.getAvailableLocales();
            Optional<Locale> any = Arrays.stream(locales).filter(item -> item.getLanguage().equals(locale)).findAny();
            return any.orElse(request.getLocale());
        }
        return request.getLocale();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) get(CommonConstant.REQUEST);
    }

    public String getTenantId() {
        return String.valueOf(getDefault(CommonConstant.TENANT_ID_PARAM, ""));
    }

    /**
     * Override the default ChainRequestContext
     *
     * @param clazz
     */
    public static void setContextClass(Class<? extends ServiceContext> clazz) {
        contextClass = clazz;
    }

    /**
     * puts the key, value into the map. a null value will remove the key from the map
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        if (value != null) {
            put(key, value);
        } else {
            remove(key);
        }
    }

    /**
     * Get the current ChainRequestContext
     *
     * @return the current ChainRequestContext
     */
    public static ServiceContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * Unsets the threadLocal context. Done at the end of the request.
     *
     * @return
     */
    public void unset() {
        this.clear();
        THREAD_LOCAL.remove();
    }

    /**
     * Returns either passed value of the key, or if the value is {@code null}, the value of {@code defaultValue}.
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getDefault(String key, Object defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

}
