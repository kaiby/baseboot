package cn.ctyun.core.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.ctyun.core.base.dao.GenericDao;
import cn.ctyun.core.common.entity.ChinaRegionsDict;

@Mapper
public interface ChinaRegionsDictDao extends GenericDao<ChinaRegionsDict, String> {

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
