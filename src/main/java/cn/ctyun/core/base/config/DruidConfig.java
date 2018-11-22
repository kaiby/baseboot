package cn.ctyun.core.base.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid监控配置
 * 
 * @author kaibyx
 * @date 2018年11月22日 下午2:22:05
 */
//@Configuration
public class DruidConfig {

    // 配置Druid的监控
    /**
     * 1、配置一个管理后台的Servlet
     * 
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();

        initParams.put(ResourceServlet.PARAM_NAME_USERNAME, "admin");
        initParams.put(ResourceServlet.PARAM_NAME_PASSWORD, "123456");
        initParams.put(ResourceServlet.PARAM_NAME_ALLOW, "");// 默认就是允许所有访问
        initParams.put(ResourceServlet.PARAM_NAME_DENY, "192.168.15.21");

        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2、配置一个web监控的filter
     * 
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return bean;
    }

}
