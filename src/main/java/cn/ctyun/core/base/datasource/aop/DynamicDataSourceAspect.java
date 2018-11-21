package cn.ctyun.core.base.datasource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ctyun.core.base.datasource.DynamicDataSourceContextHolder;
import cn.ctyun.core.base.datasource.annotation.DataSource;

/**
 * 数据源AOP拦截
 * 
 * @Description 此种方式AOP不起作用，已换其他方式实现
 * @author kaibyx
 * @date 2018年11月21日 上午11:06:07
 */
// @Aspect
// @Component
@Deprecated
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds) throws Throwable {
        String dsId = ds.value();
        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            logger.debug("Use DataSource :{} >", dsId, point.getSignature());
        } else {
            logger.info("DataSource[{}] not exist，use default DataSource >{}", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
        // logger.debug("Revert DataSource : " + ds.value() + " > " +
        // point.getSignature());
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();

    }

}
