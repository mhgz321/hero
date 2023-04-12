package com.keaizhale.hero.uaa.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.keaizhale.hero.common.bean.R;
import com.keaizhale.hero.uaa.controller.dto.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * description: TestController
 * date: 2023/3/24 13:25
 * author: keaizhale
 * version: 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public R<String> login(@Validated @RequestBody LoginUser loginUser) {
        System.out.println("1");
        StpUtil.login(1232);
        return R.succeed(StpUtil.getTokenValue());
    }
}
