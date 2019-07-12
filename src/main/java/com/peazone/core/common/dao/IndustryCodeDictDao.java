package com.peazone.core.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.peazone.core.base.dao.GenericDao;
import com.peazone.core.common.entity.IndustryCodeDict;

@Mapper
public interface IndustryCodeDictDao extends GenericDao<IndustryCodeDict, String> {

    /**
     * 根据ID查询信息
     * 
     * @param id
     *            ID
     * @return
     */
    public IndustryCodeDict getById(@Param("id") String id);
    
    /**
     * 根据行业编码查询
     * @param code 行业编码
     * @return
     */
    public IndustryCodeDict getByCode(@Param("code") String code);

    /**
     * 查询行业标识集合
     * 
     * @param paramBean
     *            参数bean
     * @return 行业标识实例集合
     */
    public List<IndustryCodeDict> getIndustryCodes(IndustryCodeDict paramBean);

}
