package cn.ctyun.core.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ctyun.core.common.dao.ChinaRegionsDictDao;
import cn.ctyun.core.common.entity.ChinaRegionsDict;
import cn.ctyun.core.common.service.ChinaRegionsDictService;

@Service("chinaRegionsDictService")
public class ChinaRegionsDictServiceImpl implements ChinaRegionsDictService {

    @Resource
    private ChinaRegionsDictDao chinaRegionsDictDao;

    @Override
    public ChinaRegionsDict getByCode(Integer code) {
        return chinaRegionsDictDao.getByCode(code);
    }

    @Override
    public List<ChinaRegionsDict> getDictList(ChinaRegionsDict paramBean) {
        return chinaRegionsDictDao.getDictList(paramBean);
    }

}
