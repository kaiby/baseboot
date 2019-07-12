package com.peazone.core.base.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonRESTResult {

    private int statusCode;
    private String message;
    private Object returnObj;

    public JsonRESTResult() {
        super();
    }

    public JsonRESTResult(int statusCode, String message, Object returnObj) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.returnObj = returnObj;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnObj() {
        return returnObj;
    }

    public void setReturnObj(Object returnObj) {
        this.returnObj = returnObj;
    }

}
