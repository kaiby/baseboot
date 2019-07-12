package com.peazone.core.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peazone.core.common.dao.ChinaRegionsDictDao;
import com.peazone.core.common.entity.ChinaRegionsDict;
import com.peazone.core.common.service.ChinaRegionsDictService;

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
