package cn.ctyun.core.crmBilling.service;

import java.io.InputStream;

import cn.ctyun.core.base.bo.BusinessBO;
import cn.ctyun.core.crmBilling.entity.BillingUploadAnswerRecord;
import cn.ctyun.core.crmBilling.entity.BillingUploadRecord;

import com.github.pagehelper.PageInfo;

public interface BillingFileService {

    public BusinessBO<BillingUploadRecord> uploadFile(String id, String txtFileName, InputStream txtInputStream, String asn1FileName, InputStream asn1InputStream, Integer isEmpty);

    public BusinessBO<BillingUploadRecord> received(String id);

    public BusinessBO<BillingUploadAnswerRecord> errorFileUpload(String errorFileName, InputStream fileInputStream);

    public PageInfo<BillingUploadRecord> getUploadFile(BillingUploadRecord uploadRecordBO, int currentPage, int pageSize);

    public PageInfo<BillingUploadAnswerRecord> getAnswerFile(BillingUploadAnswerRecord uploadAnswerRecordBO, int currentPage, int pageSize);

    public byte[] downloadFtpFile(String filePath);
    
    public InputStream download(String filePath);

}
