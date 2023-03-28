package com.keaizhale.file.controller;

import com.keaizhale.hero.common.bean.R;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api")
public class OpenController {
    @GetMapping("/t")
    public R<String> test(HttpServletRequest request) {
        String s = request.getParameter("t");
        System.out.println("文件中心响应：" + s);
        return R.succeed("响应数据avc："+ s, "success");
    }
}