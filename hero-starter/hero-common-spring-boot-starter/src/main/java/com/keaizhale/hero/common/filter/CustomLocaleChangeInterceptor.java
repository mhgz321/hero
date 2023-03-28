package com.keaizhale.hero.common.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

/**
 * description: CustomLocaleChangeInterceptor
 * date: 2023/3/27 10:25
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public class CustomLocaleChangeInterceptor extends LocaleChangeInterceptor {
    /**
     * try to get locale from header, if not exist then get it from request parameter.
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return bool
     * @throws ServletException ServletException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws ServletException {
        String newLocale = request.getHeader(getParamName());
        if (checkHttpMethods(request.getMethod())) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver==null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }
            try {
                localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
            } catch (IllegalArgumentException ex) {
                if (isIgnoreInvalidLocale()) {
                    log.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
                } else {
                    throw ex;
                }
            }
        }
        return true;
    }

    private boolean checkHttpMethods(String currentMethod) {
        String[] configuredMethods = getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        }
        for (String configuredMethod : configuredMethods) {
            if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Locale parseLocaleValue(String localeValue) {
        if (StringUtils.hasLength(localeValue)) {
            return StringUtils.parseLocale(localeValue);
        }
       return Locale.SIMPLIFIED_CHINESE;
    }
}
