package com.hong.entity.bean;

/**
 * 向前端返回信息封装
 * @param <T> 可变类型
 */
public class Result<T> {
    //返回信息
    private String msg;
    //数据是否正常请求
    private Boolean successFlag;
    //具体返回的数据
    private T detail;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}