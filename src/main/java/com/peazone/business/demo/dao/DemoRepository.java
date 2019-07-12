package com.peazone.business.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.peazone.business.demo.entity.DemoTable;

public interface DemoRepository extends MongoRepository<DemoTable, String> {
    
    public DemoTable findByUserName(String name);

}
