package com.peazone.business.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.peazone.business.demo.entity.DemoTable;
import com.peazone.core.base.dao.GenericDao;

@Mapper
public interface DemoDao extends GenericDao<DemoTable, String> {

    /**
     * 根据ID信息
     * 
     * @param id
     *            ID
     * @return
     */
    public DemoTable getById(@Param("id") String id);

}
