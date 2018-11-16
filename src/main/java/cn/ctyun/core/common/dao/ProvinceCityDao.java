package cn.ctyun.core.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.ctyun.core.base.dao.GenericDao;
import cn.ctyun.core.common.entity.ProvinceCity;

@Mapper
public interface ProvinceCityDao extends GenericDao<ProvinceCity, String> {

    List<ProvinceCity> findCityByProvince(String provinceCode);

    List<ProvinceCity> findAllProvince();

    List<ProvinceCity> findAll();

    ProvinceCity findByOldCityCode(String oldCityCode);

    ProvinceCity findCityNoByProvinceCityCode(String provinceCode, String cityCode);
}
