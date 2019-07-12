package com.peazone.core.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peazone.core.common.dao.ProvinceCityDao;
import com.peazone.core.common.entity.ProvinceCity;
import com.peazone.core.common.service.ProvinceCityService;

@Service("provinceCityService")
public class ProvinceCityServiceImpl implements ProvinceCityService {
    @Resource
    ProvinceCityDao provinceCityDao;

    @Override
    public List<ProvinceCity> findCityByProvince(String provinceCode) {
        if (provinceCode == null) {
            return null;
        }
        return provinceCityDao.findCityByProvince(provinceCode);
    }

    @Override
    public List<ProvinceCity> findAllProvince() {
        return provinceCityDao.findAllProvince();
    }

    @Override
    public List<ProvinceCity> findAll() {
        return provinceCityDao.findAll();
    }

    @Override
    public ProvinceCity findByOldCityCode(String oldCityCode) {
        return provinceCityDao.findByOldCityCode(oldCityCode);
    }

    @Override
    public ProvinceCity findCityNoByProvinceCityCode(String provinceCode, String cityCode) {
        return provinceCityDao.findCityNoByProvinceCityCode(provinceCode, cityCode);
    }

    @Override
    public ProvinceCity findById(String id) {
        return provinceCityDao.getById(id);
    }

    @Override
    public int updatePromise(String id, String isPromise) {
        ProvinceCity provinceCity = provinceCityDao.getById(id);
        if (provinceCity == null) {
            return 0;
        }
        provinceCity.setIsPromise(Integer.parseInt(isPromise));
        return provinceCityDao.update(provinceCity);
    }

}
