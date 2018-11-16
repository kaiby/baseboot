package cn.ctyun.core.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.ctyun.core.base.dao.GenericDao;
import cn.ctyun.core.demo.entity.DemoTable;

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
