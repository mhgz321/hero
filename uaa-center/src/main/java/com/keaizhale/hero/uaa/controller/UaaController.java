package com.keaizhale.hero.uaa.controller;

import com.keaizhale.hero.common.bean.R;
import com.keaizhale.hero.file.api.FileOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * description: TestController
 * date: 2023/3/24 13:25
 * author: keaizhale
 * version: 1.0
 */
@RestController
public class UaaController {
    @Autowired
    private FileOpenApi fileOpenApi;

    @GetMapping("/uaa/test")
    public R<String> test() {
        Mono<R<String>> m = fileOpenApi.getById();
        return m.block();
    }
}
