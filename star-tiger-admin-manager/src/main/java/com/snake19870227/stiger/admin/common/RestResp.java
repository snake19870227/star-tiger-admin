package com.snake19870227.stiger.admin.common;

import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.core.context.StarTigerContext;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/24
 */
public class RestResp<T> {

    private String code;

    private String msg;

    private T data;

    public static <T> RestResp<T> buildResp(String code, T data) {
        String msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + code);
        return createResp(code, msg, data);
    }

    public static <T> RestResp<T> buildResp(String code, T data, Object... placeholders) {
        String msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + code, placeholders);
        return createResp(code, msg, data);
    }

    public static <T> RestResp<T> buildResp(String code, Exception ex) {
        String msg;
        if (ex != null) {
            msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + code, ex.getMessage());
        } else {
            msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + code);
        }
        return createResp(code, msg, null);
    }

    public static <T> RestResp<T> buildResp(String code, String msg) {
        return createResp(code, msg, null);
    }

    public static <T> RestResp<T> buildResp(String code) {
        String msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + code);
        return createResp(code, msg, null);
    }

    public static <T> RestResp<T> createResp(String code, String msg, T data) {
        RestResp<T> resp = new RestResp<>();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setData(data);
        return resp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
