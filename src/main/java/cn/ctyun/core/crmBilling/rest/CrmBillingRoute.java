package cn.ctyun.core.crmBilling.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.ctyun.core.base.bo.BusinessBO;
import cn.ctyun.core.base.rest.JsonRESTResult;
import cn.ctyun.core.base.rest.RESTStatusCode;
import cn.ctyun.core.base.utils.JsonUtils;
import cn.ctyun.core.crmBilling.entity.BillingUploadAnswerRecord;
import cn.ctyun.core.crmBilling.entity.BillingUploadRecord;
import cn.ctyun.core.crmBilling.service.BillingFileService;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

@Controller
@RequestMapping("/crmBilling/")
public class CrmBillingRoute {

    private static final Logger logger = LoggerFactory.getLogger(CrmBillingRoute.class);

    @Resource
    private BillingFileService billingFileService;

    @RequestMapping(value = "fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonRESTResult fileUpload(@RequestParam("txtFile") MultipartFile txtFile, @RequestParam("asn1File") MultipartFile asn1File, @RequestParam(value = "id", required = true) String id, @RequestParam(value = "isEmpty", required = true) Integer isEmpty) {
        JsonRESTResult result = new JsonRESTResult();

        try {
            BusinessBO<BillingUploadRecord> bb = billingFileService.uploadFile(id, txtFile.getOriginalFilename(), txtFile.getInputStream(), asn1File.getOriginalFilename(), asn1File.getInputStream(), isEmpty);
            result.setReturnObj(bb.getData());
            result.setStatusCode(RESTStatusCode.SUCCESS);
        } catch (Exception e) {
            logger.error("获取文件流出错，error:", e);
            result.setStatusCode(RESTStatusCode.ERROR);
        }

        return result;
    }

    @RequestMapping(value = "received", method = RequestMethod.POST)
    @ResponseBody
    public JsonRESTResult received(@RequestParam String id) {
        JsonRESTResult result = new JsonRESTResult();
        BusinessBO<BillingUploadRecord> bb = billingFileService.received(id);
        result.setReturnObj(bb.getData());
        result.setStatusCode(RESTStatusCode.SUCCESS);
        return result;
    }

    @RequestMapping(value = "errorFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public JsonRESTResult errorFileUpload(@RequestParam("errorFile") MultipartFile errorFile) {
        JsonRESTResult result = new JsonRESTResult();

        try {
            BusinessBO<BillingUploadAnswerRecord> bb = billingFileService.errorFileUpload(errorFile.getOriginalFilename(), errorFile.getInputStream());
            result.setReturnObj(bb.getData());
            result.setStatusCode(RESTStatusCode.SUCCESS);
        } catch (Exception e) {
            logger.error("获取文件流出错，error:", e);
            result.setStatusCode(RESTStatusCode.ERROR);
        }

        return result;
    }

    @RequestMapping(value = "getUploadFile", method = RequestMethod.POST)
    @ResponseBody
    public JsonRESTResult getUploadFile(@RequestParam(value = "paramJson", required = true) String paramJson, @RequestParam int currentPage, @RequestParam int pageSize) {
        JsonRESTResult result = new JsonRESTResult();

        try {
            BillingUploadRecord uploadRecordBO = JsonUtils.fromJson(paramJson, BillingUploadRecord.class);
            PageInfo<BillingUploadRecord> uploadRecords = billingFileService.getUploadFile(uploadRecordBO, currentPage, pageSize);

            result.setReturnObj(uploadRecords);
            result.setStatusCode(RESTStatusCode.SUCCESS);

        } catch (IOException e) {
            logger.error("检索失败", e);
            result.setMessage("检索失败");
            result.setStatusCode(RESTStatusCode.FAIL);
        }

        return result;
    }

    @RequestMapping(value = "getAnswerFile", method = RequestMethod.POST)
    @ResponseBody
    public JsonRESTResult getAnswerFile(@RequestParam(value = "paramJson", required = true) String paramJson, @RequestParam int currentPage, @RequestParam int pageSize) {
        JsonRESTResult result = new JsonRESTResult();

        try {
            BillingUploadAnswerRecord uploadAnswerRecordBO = JsonUtils.fromJson(paramJson, BillingUploadAnswerRecord.class);
            PageInfo<BillingUploadAnswerRecord> uploadRecords = billingFileService.getAnswerFile(uploadAnswerRecordBO, currentPage, pageSize);

            result.setReturnObj(uploadRecords);
            result.setStatusCode(RESTStatusCode.SUCCESS);

        } catch (IOException e) {
            logger.error("检索失败", e);
            result.setMessage("检索失败");
            result.setStatusCode(RESTStatusCode.FAIL);
        }

        return result;
    }

    @RequestMapping(value = "downloadFtpFile", params = "filePath", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFtpFile(@RequestParam(value = "filePath", required = true) String filePath) {
        byte[] FileByte = billingFileService.downloadFtpFile(filePath);
        if (FileByte != null) {
            Iterable<String> splitterIt = Splitter.on("/").split(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", Iterables.getLast(splitterIt)));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(new ByteArrayInputStream(FileByte)));
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "download", params = "filePath", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download(@RequestParam(value = "filePath", required = true) String filePath) {
        InputStream inputStream = billingFileService.download(filePath);
        if (inputStream != null) {
            Iterable<String> splitterIt = Splitter.on("/").split(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", Iterables.getLast(splitterIt)));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(inputStream));
        } else {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

    }

}
