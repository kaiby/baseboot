package com.peazone.core.base.bo;

public class BusinessBO<T> {

    private T data;
    private boolean isSuccess;
    private String code;
    private String msg;

    public BusinessBO(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public BusinessBO(T data, boolean isSuccess, String code, String msg) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
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


}
