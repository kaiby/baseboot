package com.peazone.core.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.peazone.core.common.dao.IndustryCodeDictDao;
import com.peazone.core.common.entity.IndustryCodeDict;
import com.peazone.core.common.service.IndustryCodeDictService;

@Service("industryCodeDictService")
public class IndustryCodeDictServiceImpl implements IndustryCodeDictService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    private IndustryCodeDictDao industryCodeDictDao;

    @Override
    public IndustryCodeDict getById(String id) {
        logger.info("----> getById id = {}", id);
        return industryCodeDictDao.getById(id);
    }

    @Override
    public IndustryCodeDict getByCode(String code) {
        logger.info("getByCode code = {}", code);
        if (Strings.isNullOrEmpty(code)) {
            return null;
        }

        return industryCodeDictDao.getByCode(code);
    }

    @Override
    public List<IndustryCodeDict> getIndustryCodes(IndustryCodeDict paramBean) {
        return industryCodeDictDao.getIndustryCodes(paramBean);
    }

    @Override
    public PageInfo<IndustryCodeDict> getIndustryCodes(IndustryCodeDict paramBean, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<IndustryCodeDict> dicts = industryCodeDictDao.getIndustryCodes(paramBean);
        PageInfo<IndustryCodeDict> pageInfo = new PageInfo<IndustryCodeDict>(dicts);
        return pageInfo;
    }
}
