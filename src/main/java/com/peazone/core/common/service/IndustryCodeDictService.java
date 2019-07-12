package com.peazone.core.common.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.peazone.core.common.entity.IndustryCodeDict;

public interface IndustryCodeDictService {

    public IndustryCodeDict getById(String id);

    /**
     * 查询指定行业标识
     * 
     * @param groupCode
     *            组编码
     * @param code
     *            属性编码
     * @return 属性实例
     */
    public IndustryCodeDict getByCode(String code);

    /**
     * 查询行业标识集合
     * 
     * @param paramBean
     *            参数bean
     * @return 行业标识集合
     */
    public List<IndustryCodeDict> getIndustryCodes(IndustryCodeDict paramBean);

    /**
     * 分页查询行业标识集合
     * 
     * @param paramBean
     *            参数bean
     * @param currentPage
     *            当前页
     * @param pageSize
     *            页大小
     * @return
     */
    public PageInfo<IndustryCodeDict> getIndustryCodes(IndustryCodeDict paramBean, int currentPage, int pageSize);
}
