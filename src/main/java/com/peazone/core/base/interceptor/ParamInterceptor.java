package com.peazone.core.base.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zhouxiaobo on 2017/6/16. 参数拦截器
 */
public class ParamInterceptor implements HandlerInterceptor {

    private final Logger logger = LogManager.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());
        String sessionId = httpServletRequest.getSession().getId();
        String url = httpServletRequest.getRequestURL().toString();
        String method = httpServletRequest.getMethod();
        // String queryString = httpServletRequest.getQueryString();

        StringBuffer sf = new StringBuffer();
        sf.append(System.getProperty("line.separator"));
        sf.append("SESSIONID: ").append(sessionId).append(System.getProperty("line.separator"));
        sf.append("URL: ").append(url).append(System.getProperty("line.separator"));
        sf.append("HTTP_METHOD: ").append(method).append(System.getProperty("line.separator"));
        sf.append("BODY PARAMS: {").append(System.getProperty("line.separator"));

        Enumeration<String> enu = httpServletRequest.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            sf.append("        ").append(paraName).append(": ").append(httpServletRequest.getParameter(paraName)).append(System.getProperty("line.separator"));
        }
        sf.append("}").append(System.getProperty("line.separator"));
        sf.append("HEADER PARAMS: {").append(System.getProperty("line.separator"));
        Enumeration<String> headerEnu = httpServletRequest.getHeaderNames();
        while (headerEnu.hasMoreElements()) {
            String paraName = headerEnu.nextElement();
            sf.append("        ").append(paraName).append(": ").append(httpServletRequest.getHeader(paraName)).append(System.getProperty("line.separator"));
        }
        sf.append("}");
        logger.info("--------> REQUEST INTERCEPTOR {}", sf.toString());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
