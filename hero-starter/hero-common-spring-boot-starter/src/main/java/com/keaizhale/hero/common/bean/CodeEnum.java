package com.keaizhale.hero.common.bean;

public enum CodeEnum {
    SUCCESS("0"),
    ERROR("1"),
    UNKNOWN_ERROR("9");

    private String code;
    CodeEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
