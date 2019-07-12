package com.peazone.core.common.service;

import java.util.List;

import com.peazone.core.common.entity.ProvinceCity;

public interface ProvinceCityService {

    public List<ProvinceCity> findCityByProvince(String provinceCode);

    public List<ProvinceCity> findAllProvince();

    public List<ProvinceCity> findAll();

    public ProvinceCity findByOldCityCode(String oldCityCode);

    public ProvinceCity findCityNoByProvinceCityCode(String provinceCode, String cityCode);

    public ProvinceCity findById(String id);

    public int updatePromise(String id, String isPromise);

}
