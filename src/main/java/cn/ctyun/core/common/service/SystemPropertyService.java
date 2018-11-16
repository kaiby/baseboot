package cn.ctyun.core.common.service;

import java.util.List;

import cn.ctyun.core.common.entity.SystemPropertyPO;

import com.github.pagehelper.PageInfo;

public interface SystemPropertyService {

    public SystemPropertyPO getById(String id);

    /**
     * 查询配置属性
     * 
     * @param groupCode
     *            组编码
     * @param code
     *            属性编码
     * @return 属性实例
     */
    public SystemPropertyPO getProperty(String groupCode, String code);

    /**
     * 查询配置集合
     * 
     * @param paramBean
     *            参数bean
     * @return 配置实例集合
     */
    public List<SystemPropertyPO> getProperties(SystemPropertyPO paramBean);

    /**
     * 分页查询配置集合
     * 
     * @param paramBean
     *            参数bean
     * @param currentPage
     *            当前页
     * @param pageSize
     *            页大小
     * @return
     */
    public PageInfo<SystemPropertyPO> getProperties(SystemPropertyPO paramBean, int currentPage, int pageSize);

    /**
     * 通过接口配置
     * @param systemPropertyPO
     */
    void addProperty(SystemPropertyPO systemPropertyPO);
}
