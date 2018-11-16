package cn.ctyun.core.demo.rest;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ctyun.core.base.controller.BaseController;
import cn.ctyun.core.base.rest.JsonRESTResult;
import cn.ctyun.core.base.rest.RESTStatusCode;
import cn.ctyun.core.demo.entity.DemoTable;
import cn.ctyun.core.demo.service.DemoService;

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
