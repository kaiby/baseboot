package com.peazone.business.demo.rest;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peazone.business.demo.entity.DemoTable;
import com.peazone.business.demo.service.DemoService;
import com.peazone.core.base.controller.BaseController;
import com.peazone.core.base.rest.JsonRESTResult;
import com.peazone.core.base.rest.RESTStatusCode;

@RestController
@RequestMapping("/demo/")
public class DemoRoute extends BaseController {

    @Resource
    private DemoService demoService;

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    public JsonRESTResult getById(@RequestParam String id) {
        DemoTable demoUser = demoService.getById(id);

        JsonRESTResult result = new JsonRESTResult();
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");
        result.setReturnObj(demoUser);

        return result;
    }

    @RequestMapping(value = "getFromMongo", method = RequestMethod.GET)
    public JsonRESTResult getFromMongo(@RequestParam String id) {

        DemoTable demoUser = demoService.testMongo();

        JsonRESTResult result = new JsonRESTResult();
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");
        result.setReturnObj(demoUser);

        return result;
    }

}
