package cn.ctyun.core.common.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.ctyun.core.base.utils.UUIDUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.ctyun.core.common.bo.SystemPropertyBO;
import cn.ctyun.core.common.dao.SystemPropertyDao;
import cn.ctyun.core.common.entity.SystemPropertyPO;
import cn.ctyun.core.common.service.SystemPropertyService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

@Service("systemPropertyService")
public class SystemPropertyServiceImpl implements SystemPropertyService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    private SystemPropertyDao systemPropertyDao;

    @Override
    public SystemPropertyPO getById(String id) {
        logger.info("----> getById id = {}", id);
        return systemPropertyDao.getById(id);
    }

    @Override
    public SystemPropertyPO getProperty(String groupCode, String code) {
        logger.info("Get Property by {}, {}", groupCode, code);
        if (Strings.isNullOrEmpty(groupCode) && Strings.isNullOrEmpty(code)) {
            return null;
        }
        SystemPropertyBO paramBean = new SystemPropertyBO();
        paramBean.setGroupCode(groupCode);
        paramBean.setCode(code);
        paramBean.setIsValid(1);
        List<SystemPropertyPO> properties = systemPropertyDao.getProperties(paramBean);
        if (properties != null && properties.size() > 0) {
            return properties.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<SystemPropertyPO> getProperties(SystemPropertyPO paramBean) {
        return systemPropertyDao.getProperties(paramBean);
    }

    @Override
    public PageInfo<SystemPropertyPO> getProperties(SystemPropertyPO paramBean, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<SystemPropertyPO> properties = systemPropertyDao.getProperties(paramBean);
        PageInfo<SystemPropertyPO> pageInfo = new PageInfo<SystemPropertyPO>(properties);
        return pageInfo;
    }

    @Override
    public void addProperty(SystemPropertyPO systemPropertyPO) {
        systemPropertyPO.setId(UUIDUtils.getUUID());
        systemPropertyPO.setIsAutoLoad(1);
        systemPropertyPO.setIsValid(1);
        systemPropertyPO.setCreateTime(new Date());
        systemPropertyPO.setUpdateTime(new Date());
        systemPropertyDao.insert(systemPropertyPO);
    }

}
