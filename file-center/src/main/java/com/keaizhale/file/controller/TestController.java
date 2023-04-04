package com.keaizhale.file.controller;

import com.keaizhale.file.message.FileErrorEnum;
import com.keaizhale.hero.common.bean.R;
import com.keaizhale.hero.common.exception.ServiceException;
import com.keaizhale.hero.common.message.ServiceErrorEnum;
import com.keaizhale.hero.logging.annotation.HeroLog;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private MessageSource messageSource;
    @GetMapping("/f-test")
    public String test(HttpServletRequest request) {
        String s = request.getParameter("t");
        System.out.println("文件中心响应：" + s);
//        Locale locale = new Locale("en");
//        String t1 = messageSource.getMessage("common.error_9001", null, locale);
//        System.out.println(t1);
        ServiceException serviceException = ServiceErrorEnum.API_COMMON_ERROR.getErrorMeta().getException("123");
        System.out.println(serviceException.getError().msg());
        ServiceException serviceException1 = FileErrorEnum.NAME_NUMM_ERROR.getErrorMeta().getException();
        System.out.println(serviceException1.getError().msg());
        throw serviceException;
    }

    @PostMapping("/valition")
    @HeroLog(name = "测试", operation = "#test.name")
    public R<Test> valition(@Validated @RequestBody Test test) {
        return R.succeed(test);
    }
}