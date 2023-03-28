package com.keaizhale.hero.file.api;

import com.keaizhale.hero.common.bean.R;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * description: FileClientApi
 * date: 2023/3/24 15:40
 * author: keaizhale
 * version: 1.0
 */
@HttpExchange(url = "/open-api")
public interface FileOpenApi {
    @GetExchange("/t")
    Mono<R<String>> getById();
}
