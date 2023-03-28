package com.keaizhale.hero.common.exception;

import com.alibaba.fastjson2.JSONObject;
import com.keaizhale.hero.common.bean.R;
import com.keaizhale.hero.common.message.ServiceErrorEnum;
import com.keaizhale.hero.common.message.ServiceErrorMeta;
import com.keaizhale.hero.common.message.ServiceError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

/**
 * description: 异常统一输出
 * date: 2023/3/23 14:18
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
public class ExceptionHolder {
    /**
     * 与网关约定好的状态码，表示业务出错
     */
    private static final int BIZ_ERROR_CODE = 4000;

    /**
     * 与网关约定好的系统错误状态码
     */
    private static final int SYSTEM_ERROR_CODE = 5050;

    /**
     * header中的错误code
     */
    private static final String X_SERVICE_ERROR_HEADER_NAME = "x-service-error-code";

    /**
     * header中的错误信息
     */
    private static final String X_SERVICE_ERROR_MESSAGE = "x-service-error-message";

    /**
     * header中的返回信息
     */
    private static final String X_SERVICE_ERROR_RESPONSE = "x-service-error-response";

    /**
     * 处理微服务异常信息，做到不与原系统的错误处理相冲突
     * @param request request
     * @param response response
     * @param exception exception
     */
    public static R hold(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        int code = exception instanceof ServiceException ? BIZ_ERROR_CODE : SYSTEM_ERROR_CODE;
        // 需要设置两个值，这样网关会收到错误信息
        // 并且会统计到监控当中
        response.setHeader(X_SERVICE_ERROR_HEADER_NAME, String.valueOf(code));
        R r = buildResponse(request, response, exception);
        response.setHeader(X_SERVICE_ERROR_RESPONSE, UriUtils.encode(JSONObject.toJSONString(r), StandardCharsets.UTF_8));

        // 如果是未知错误，还需要收集异常信息
        if (code == SYSTEM_ERROR_CODE) {
            StringBuilder msg = new StringBuilder();
            msg.append(exception.getMessage());
            StackTraceElement[] stackTrace = exception.getStackTrace();
            // 取5行错误内容
            int lineCount = 5;
            for (int i = 0; i < stackTrace.length && i < lineCount; i++) {
                StackTraceElement stackTraceElement = stackTrace[i];
                msg.append("\n at ").append(stackTraceElement.toString());
            }
            response.setHeader(X_SERVICE_ERROR_MESSAGE, UriUtils.encode(msg.toString(), StandardCharsets.UTF_8));
        }

        return r;
    }

    /**
     * 处理异常
     *
     * @param request   request
     * @param response  response
     * @param exception 异常信息
     * @return 返回最终结果
     */
    private static R buildResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ServiceError error;
        if (exception instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) exception;
            error = serviceException.getError();
            log.debug("业务处理异常：{},{}", error.code(), error.msg());
        } else {
            ServiceErrorMeta errorMeta = ServiceErrorEnum.API_UNKNOWN_ERROR.getErrorMeta();
            error = errorMeta.getError();
            log.debug("系统中发生未知异常：", exception);
        }
        return R.of(error.code(), error.msg());

    }
}
