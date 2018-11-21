package cn.ctyun.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import cn.ctyun.core.base.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import cn.ctyun.core.base.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import cn.ctyun.core.base.datasource.register.DynamicDataSourceRegister;

/**
 * 
 * @author kaibyx
 * @date 2018年11月20日 下午4:26:13
 */
@SpringBootApplication
@EnableAsync
@Import(DynamicDataSourceRegister.class)
public class Application {

    /**
     * 引入动态数据源管理
     * 
     * @return
     */
    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
