package com.peazone.business.demo.service;

import com.peazone.business.demo.entity.DemoTable;

public interface DemoService {

    public DemoTable getById(String id);

    public DemoTable testMongo();
}
