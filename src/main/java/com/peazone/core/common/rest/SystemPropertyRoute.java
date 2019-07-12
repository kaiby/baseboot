package com.peazone.core.common.rest;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.peazone.core.base.controller.BaseController;
import com.peazone.core.base.rest.JsonRESTResult;
import com.peazone.core.base.rest.RESTStatusCode;
import com.peazone.core.base.utils.JsonUtils;
import com.peazone.core.base.utils.PropertyUtils;
import com.peazone.core.common.entity.SystemPropertyPO;
import com.peazone.core.common.service.SystemPropertyService;

@RestController
@RequestMapping("/common/")
public class SystemPropertyRoute extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(SystemPropertyRoute.class);

    @Resource
    private SystemPropertyService systemPropertyService;

    @RequestMapping(value = "getPropertyById", method = RequestMethod.GET)
    public JsonRESTResult getPropertyById(@RequestParam("id") String id) {
        JsonRESTResult result = new JsonRESTResult();
        SystemPropertyPO property = systemPropertyService.getById(id);
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");
        result.setReturnObj(property);

        return result;
    }

    @RequestMapping(value = "getProperty", method = RequestMethod.POST)
    public JsonRESTResult getProperty(@RequestParam String groupCode, @RequestParam String code) {
        JsonRESTResult result = new JsonRESTResult();
        SystemPropertyPO property = systemPropertyService.getProperty(groupCode, code);
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");
        result.setReturnObj(property);

        return result;
    }

    @RequestMapping(value = "getProperties", method = RequestMethod.POST)
    public JsonRESTResult getProperties(@RequestParam String jsonParam,@RequestParam int currentPage,@RequestParam int pageSize) {
        JsonRESTResult result = new JsonRESTResult();
        SystemPropertyPO paramBean = null;
        try {
            paramBean = JsonUtils.fromJson(jsonParam, SystemPropertyPO.class);

        } catch (IOException e1) {
            result.setStatusCode(RESTStatusCode.SUCCESS);
            result.setMessage("json参数格式错误");

            return result;
        }
        PageInfo<SystemPropertyPO> properties = systemPropertyService.getProperties(paramBean,currentPage,pageSize);

        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");
        result.setReturnObj(properties);

        return result;
    }

    @RequestMapping(value = "reloadProperty", method = RequestMethod.POST)
    public JsonRESTResult reloadProperty(@RequestParam String groupCode, @RequestParam String code) {
        PropertyUtils.reloadProperty(groupCode, code);
        JsonRESTResult result = new JsonRESTResult();
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");

        return result;
    }

    @RequestMapping(value = "reloadAll", method = RequestMethod.POST)
    public JsonRESTResult reloadAll() {
        PropertyUtils.reload();
        JsonRESTResult result = new JsonRESTResult();
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setMessage("成功");

        return result;
    }

    @PostMapping(value = "addSysProperty")
    public JsonRESTResult addSysProperty(@RequestParam(value = "sysProperty", required = true) String sysProperty) {
        JsonRESTResult result = new JsonRESTResult();
        if (Strings.isNullOrEmpty(sysProperty)) {
            result.setStatusCode(RESTStatusCode.BAD_REQUEST);
        } else {
            try {
                SystemPropertyPO systemPropertyPO = JsonUtils.fromJson(sysProperty, SystemPropertyPO.class);
                systemPropertyService.addProperty(systemPropertyPO);
                result.setStatusCode(RESTStatusCode.SUCCESS);
            } catch (IOException e) {
                result.setStatusCode(RESTStatusCode.BAD_REQUEST);
                logger.error("addSysProperty = {}", sysProperty, e.getMessage());
                result.setStatusCode(RESTStatusCode.FAIL);
            }
        }
        return result;
    }
}
