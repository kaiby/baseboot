package cn.ctyun.core.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ctyun.core.demo.entity.DemoTable;

public interface DemoRepository extends MongoRepository<DemoTable, String> {
    
    public DemoTable findByUserName(String name);

}
