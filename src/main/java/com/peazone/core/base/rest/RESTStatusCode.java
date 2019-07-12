package com.peazone.core.base.rest;

/**
 * REST的返回值接口
 * 
 */
public interface RESTStatusCode {
    /* HTTP相关状态码 */
    int OK = 200;
    int BAD_REQUEST = 400;
    int NOT_FOUND = 404;
    int ERROR = 500;

    /* 业务相关状态码 */
    int SUCCESS = 800;
    int NULL_SUCCESS = 801;
    int FAIL = 900;
    int NULLFAIL = 300;

    /* 系统相关代码 */
    int PARAM_QUERY = 1000;
    int PERF_QUERY = 1001;
}
