package com.peazone.core.common.service;

import java.util.List;

import com.peazone.core.common.entity.ChinaRegionsDict;

public interface ChinaRegionsDictService {

    /**
     * 通过行政区划编码查询
     * 
     * @param code
     *            行政区划编码
     * @return 行政区划实例
     */
    public ChinaRegionsDict getByCode(Integer code);

    /**
     * 查询行政区划列表
     * 
     * @param paramBean
     *            查询参数bean
     * @return 行政区划列表
     */
    public List<ChinaRegionsDict> getDictList(ChinaRegionsDict paramBean);

}
