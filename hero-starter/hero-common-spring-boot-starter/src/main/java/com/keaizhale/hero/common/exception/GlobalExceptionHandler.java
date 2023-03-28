package com.keaizhale.hero.common.exception;

import com.keaizhale.hero.common.bean.R;
import com.keaizhale.hero.common.context.ServiceContext;
import com.keaizhale.hero.common.message.ServiceErrorEnum;
import com.keaizhale.hero.common.message.ServiceErrorFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description: 全局统一异常处理
 * date: 2023/3/23 14:27
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String LEFT_TOKEN = "{";
    private static final String RIGHT_TOKEN = "}";
    private static final String EQ = "=";
    private static final String COMMA = ",";
    private static Object[] EMPTY_OBJ_ARRAY = {};

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected R exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        return ExceptionHolder.hold(request, response, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected R exceptionHandler(MethodArgumentNotValidException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        return getValidateBizParamException(allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    protected R exceptionHandler(HttpMessageNotReadableException exception) {
        log.warn("请求参数转换异常: "+ exception.getMessage());
        return R.failed("请求参数异常，请检查参数格式");
    }
    private R getValidateBizParamException(List<String> errorMsgs) {
        String code = ServiceErrorEnum.API_PARAMS_ERROR.getErrorMeta().getCode();
        if (errorMsgs == null || errorMsgs.size() == 0) {
            return R.failed(code, "参数校验失败");
        }

        List<String> errors = new ArrayList<>();
        for (String errorMsg: errorMsgs) {
            String[] msgToken = errorMsg.split(EQ);
            String msg = msgToken[0];
            if (msg.startsWith(LEFT_TOKEN) && msg.endsWith(RIGHT_TOKEN)) {
                String module = msg.substring(1, msg.length() - 1);
                Object[] params = this.buildParams(msgToken);
                String error = ServiceErrorFactory.getErrorMessage(module, ServiceContext.getCurrentContext().getLocale(), params);
                errors.add(error);
            } else {
                errors.add(errorMsg);
            }
        }
        return R.failed(code, errors.stream().collect(Collectors.joining(";")));
    }

    private Object[] buildParams(String[] msgToken) {
        if (msgToken.length == 2) {
            return msgToken[1].split(COMMA);
        } else {
            return EMPTY_OBJ_ARRAY;
        }
    }
}
