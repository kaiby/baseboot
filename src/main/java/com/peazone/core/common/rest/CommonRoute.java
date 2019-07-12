package com.peazone.core.common.rest;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peazone.core.base.controller.BaseController;
import com.peazone.core.base.freeMarker.TemplateUtils;
import com.peazone.core.base.rest.JsonRESTResult;
import com.peazone.core.base.rest.RESTStatusCode;
import com.peazone.core.base.utils.JsonUtils;
import com.peazone.core.base.utils.VCodeTools;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/common/")
public class CommonRoute extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(CommonRoute.class);

    @RequestMapping(value = "sendVerifyVCode", method = RequestMethod.POST)
    public JsonRESTResult sendVerifyVCode(@RequestParam String businessCode, @RequestParam String mobilePhone, @RequestParam String vCode) {
        JsonRESTResult result = new JsonRESTResult();
        int verifyResult = VCodeTools.sendVerifyVCode(businessCode, mobilePhone, vCode);
        if (verifyResult == -1) {
            result.setMessage("参数为空");
        } else if (verifyResult == 0) {
            result.setMessage("验证码已发送");
        } else if (verifyResult == 1) {
            result.setMessage("验证码有效");
        } else if (verifyResult == 2) {
            result.setMessage("验证码已失效");
        } else if (verifyResult == 3) {
            result.setMessage("验证码错误");
        } else if (verifyResult == 99) {
            result.setMessage("验证码发送失败");
        }
        result.setStatusCode(RESTStatusCode.SUCCESS);
        result.setReturnObj(verifyResult);
        return result;
    }

    /**
     * 渲染模板
     * 
     * @param dataJson
     *            json格式数据
     * @param templateName
     *            模板名称
     * @param templateContent
     *            模板内容
     * @return 渲染后模板
     */
    @RequestMapping(value = "renderTemplate", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String renderTemplate(@RequestParam String dataJson, @RequestParam String templateName, @RequestParam String templateContent) {
        String renderedTemplate = null;
        try {
            Map<String, Object> dataMap = JsonUtils.parseMap(dataJson);
            renderedTemplate = TemplateUtils.renderTemplate(dataMap, templateName, templateContent);
        } catch (IOException | TemplateException e) {
            logger.error("renderTemplate error:", e);
        }

        return renderedTemplate;
    }

}
