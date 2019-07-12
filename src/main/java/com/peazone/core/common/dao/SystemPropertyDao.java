package com.peazone.core.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.peazone.core.base.dao.GenericDao;
import com.peazone.core.common.entity.SystemPropertyPO;

@Mapper
public interface SystemPropertyDao extends GenericDao<SystemPropertyPO, String> {

    /**
     * 根据ID查询信息
     * 
     * @param id
     *            ID
     * @return
     */
    public SystemPropertyPO getById(@Param("id") String id);

    /**
     * 查询配置集合
     * 
     * @param paramBean
     *            参数bean
     * @return 配置实例集合
     */
    public List<SystemPropertyPO> getProperties(SystemPropertyPO paramBean);

}
