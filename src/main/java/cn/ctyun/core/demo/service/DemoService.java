package cn.ctyun.core.demo.service;

import cn.ctyun.core.demo.entity.DemoTable;

public interface DemoService {

    public DemoTable getById(String id);

    public DemoTable testMongo();
}
