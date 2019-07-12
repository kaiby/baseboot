package com.peazone.business.demo.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.peazone.business.demo.dao.DemoDao;
import com.peazone.business.demo.dao.DemoRepository;
import com.peazone.business.demo.entity.DemoTable;
import com.peazone.business.demo.service.DemoService;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Resource
    private DemoDao demoDao;

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public DemoTable getById(String id) {
        logger.info("----> getById id = {}", id);
        return demoDao.getById(id);
    }

    @Override
    public DemoTable testMongo() {

        DemoTable dd = new DemoTable();
        dd.setId("123456");
        dd.setEmail("fasfd@123.com");
        dd.setUserName("tom");
        dd.setPassword("123456");
        dd.setCreateTime(new Date());
        // demoRepository.save(dd);
        DemoTable demoUser = demoRepository.findByUserName("tom");

        // 条件查询
        Query query = new Query(Criteria.where("userName").is("tom"));
        demoUser = mongoTemplate.findOne(query, DemoTable.class);
        return demoUser;
    }
}
