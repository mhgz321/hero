package com.keaizhale.hero.uaa.controller.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * description: LoginUser
 * date: 2023/4/6 9:08
 * author: keaizhale
 * version: 1.0
 */
public record LoginUser(@NotBlank(message = "{login.username.notNull}")String username
        , @NotBlank(message = "{login.password.notNull}")String password) {
}
