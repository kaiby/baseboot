package com.peazone.core.base.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.peazone.core.common.entity.SystemPropertyPO;
import com.peazone.core.common.service.SystemPropertyService;

@Component
public class PropertyUtils {

    private static final Logger logger = LogManager.getLogger(PropertyUtils.class);
    private static Configuration config = new CombinedConfiguration();

    @Autowired
    private SystemPropertyService systemPropertyService;
    private static PropertyUtils propertyUtils;

    @PostConstruct
    private void init() {
        propertyUtils = this;
        propertyUtils.systemPropertyService = this.systemPropertyService;
        loadPropertiesFromDB();
    }

    private PropertyUtils() {
        super();
    }

    /**
     * 从数据库加载属性
     */
    private static void loadPropertiesFromDB() {
        SystemPropertyPO paramBean = new SystemPropertyPO();
        paramBean.setIsAutoLoad(1);
        paramBean.setIsValid(1);
        List<SystemPropertyPO> properties = propertyUtils.systemPropertyService.getProperties(paramBean);
        if (properties != null && properties.size() > 0) {
            for (Iterator<SystemPropertyPO> iterator = properties.iterator(); iterator.hasNext();) {
                SystemPropertyPO systemPropertyPO = iterator.next();
                String key = systemPropertyPO.getGroupCode() + "_" + systemPropertyPO.getCode();
                config.addProperty(key, systemPropertyPO.getValue());
            }
        }
    }

    /**
     * 重新加载属性
     * 
     * @param groupCode
     *            组编码
     * @param code
     *            属性编码
     */
    public static void reloadProperty(String groupCode, String code) {
        SystemPropertyPO property = propertyUtils.systemPropertyService.getProperty(groupCode, code);
        if (property != null) {
            String key = property.getGroupCode() + "_" + property.getCode();
            config.setProperty(key, property.getValue());
        } else {
            logger.info("Property is not exist for group is {}, name is {}", groupCode, code);
        }
    }

    /**
     * 重新加载组属性
     * 
     * @param groupCode
     *            组编码
     */
    public static void reloadProperty(String groupCode) {
        SystemPropertyPO paramBean = new SystemPropertyPO();
        paramBean.setIsValid(1);
        paramBean.setGroupCode(groupCode);
        List<SystemPropertyPO> properties = propertyUtils.systemPropertyService.getProperties(paramBean);
        if (properties != null) {
            for (Iterator<SystemPropertyPO> iterator = properties.iterator(); iterator.hasNext();) {
                SystemPropertyPO property = iterator.next();
                String key = property.getGroupCode() + "_" + property.getCode();
                config.setProperty(key, property.getValue());
            }
        } else {
            logger.info("Property is not exist for group is {}", groupCode);
        }
    }

    /**
     * 重新加载所有属性
     */
    public static void reload() {
        config.clear();
        loadPropertiesFromDB();
    }

    /**
     * 是否存在指定属性
     * 
     * @param groupCode
     * @param code
     * @return
     */
    public static boolean containsKey(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.containsKey(key);
    }

    public static Iterator<String> getKeys() {
        return config.getKeys();
    }

    public static Object getProperty(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getProperty(key);
    }

    public static boolean getBoolean(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getBoolean(key);
    }

    public static int getInt(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getInt(key);
    }

    public static int getIntValue(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getInt(key);
    }

    public static long getLong(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getLong(key);
    }

    public static long getLongValue(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getLong(key);
    }

    public static double getDouble(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getDouble(key);
    }

    public static double getDoubleValue(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getDouble(key);
    }

    public static BigDecimal getBigDecimal(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getBigDecimal(key);
    }

    public static BigInteger getBigInteger(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getBigInteger(key);
    }

    public static String getStringValue(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getString(key);
    }

    public static String[] getStringArray(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getStringArray(key);
    }

    public static List<Object> getList(String groupCode, String code) {
        String key = groupCode + "_" + code;
        return config.getList(key);
    }

    /**
     * 获取系统核心路由地址，即：getStringValue("SYS_BASE", "CORE_ROUTER_URL");
     * 
     * @return
     */
    public static String getCoreRouterUrl() {
        return getStringValue("SYS_BASE", "CORE_ROUTER_URL");
    }

    public static void main(String[] args) {
        System.out.println(PropertyUtils.getStringValue("test", "Name"));
    }

}
