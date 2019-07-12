package com.peazone.core.base.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.peazone.core.common.entity.IndustryCodeDict;
import com.peazone.core.common.entity.ProvinceCity;
import com.peazone.core.common.service.IndustryCodeDictService;
import com.peazone.core.common.service.ProvinceCityService;

@Component
public class SystemInitDataConfig {

    private static SystemInitDataConfig systemInitDataConfig;
    private static List<IndustryCodeDict> industryCodes;
    private static List<ProvinceCity> provinceCitys;

    @Autowired
    private IndustryCodeDictService industryCodeDictService;

    @Autowired
    private ProvinceCityService provinceCityService;

    @PostConstruct
    private void init() {
        systemInitDataConfig = this;
        systemInitDataConfig.industryCodeDictService = this.industryCodeDictService;
        loadIndustryCodeFromDB();
        loadProvinceCityFromDB();
    }

    private SystemInitDataConfig() {
        super();
    }

    private static void loadIndustryCodeFromDB() {
        industryCodes = systemInitDataConfig.industryCodeDictService.getIndustryCodes(null);
    }

    private static void loadProvinceCityFromDB() {
        provinceCitys = systemInitDataConfig.provinceCityService.findAll();
    }

    public static List<IndustryCodeDict> getIndustryCodes() {
        return industryCodes;
    }

    public static void setIndustryCodes(List<IndustryCodeDict> industryCodes) {
        SystemInitDataConfig.industryCodes = industryCodes;
    }

    public static List<ProvinceCity> getProvinceCitys() {
        return provinceCitys;
    }

    public static void setProvinceCitys(List<ProvinceCity> provinceCitys) {
        SystemInitDataConfig.provinceCitys = provinceCitys;
    }

}
