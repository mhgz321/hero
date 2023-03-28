package com.keaizhale.file.exception;

import com.keaizhale.hero.common.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * description: 重写公共异常拦截
 * date: 2023/3/27 10:05
 * author: keaizhale
 * version: 1.0
 */
@Slf4j
@ControllerAdvice
public class FileExceptionHandler extends GlobalExceptionHandler {
}
