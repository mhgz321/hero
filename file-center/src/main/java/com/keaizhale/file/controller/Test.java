package com.keaizhale.file.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * description: Test
 * date: 2023/3/27 9:52
 * author: keaizhale
 * version: 1.0
 */
@Data
public class Test {
    @NotBlank(message = "{test.name.notNull}")
    @Length(min = 3, max = 20, message = "{test.name.length}=3,20")
    private String name;
}
