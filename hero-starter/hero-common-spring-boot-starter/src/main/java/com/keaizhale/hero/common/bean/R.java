package com.keaizhale.hero.common.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wlb
 */
public record R<T>(String code, String msg, T datas, Map<String, Object> extend) implements Serializable {
    public static <T> R<T> succeed(String msg) {
        return of(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> succeed(T model, String msg) {
        return of(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> succeed(T model) {
        return of(model, CodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> R<T> succeed(T model, String msg, Map<String, Object> extend) {
        return of(model, CodeEnum.SUCCESS.getCode(), msg, extend);
    }

    public static <T> R<T> of(String code, String msg) {
        return new R<>(code, msg, null, null);
    }

    public static <T> R<T> of(T datas, String code, String msg) {
        return new R<>(code, msg, datas, null);
    }

    public static <T> R<T> of(T datas, String code, String msg, Map<String, Object> extend) {
        return new R<>(code, msg, datas, extend);
    }

    public static <T> R<T> failed(String msg) {
        return of(CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> R<T> failed(String code, String msg) {
        return of(code, msg);
    }

}
