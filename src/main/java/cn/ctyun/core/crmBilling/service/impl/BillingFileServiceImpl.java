package cn.ctyun.core.crmBilling.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.ctyun.core.base.bo.BusinessBO;
import cn.ctyun.core.base.utils.UUIDUtils;
import cn.ctyun.core.crmBilling.bo.BillingFileBO;
import cn.ctyun.core.crmBilling.constant.AnswerTypeEnum;
import cn.ctyun.core.crmBilling.constant.UploadRecordStatusEnum;
import cn.ctyun.core.crmBilling.dao.BillingUploadAnswerRecordRepository;
import cn.ctyun.core.crmBilling.dao.BillingUploadRecordRepository;
import cn.ctyun.core.crmBilling.entity.BillingUploadAnswerRecord;
import cn.ctyun.core.crmBilling.entity.BillingUploadRecord;
import cn.ctyun.core.crmBilling.service.BillingFileService;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

@Service("billingFileService")
public class BillingFileServiceImpl implements BillingFileService {

    private static final Logger logger = LoggerFactory.getLogger(BillingFileServiceImpl.class);

    @Autowired
    private BillingUploadRecordRepository uploadRecordRepository;

    @Autowired
    private BillingUploadAnswerRecordRepository answerRecordRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public BusinessBO<BillingUploadRecord> uploadFile(String id, String txtFileName, InputStream txtInputStream, String asn1FileName, InputStream asn1InputStream, Integer isEmpty) {
        String yyyyMMdd = txtFileName.substring(4, 12);
        String txtFilePath = "/txt/" + yyyyMMdd + "/" + txtFileName;
        String asn1FilePath = "/asn1/" + yyyyMMdd + "/" + asn1FileName;

        BillingUploadRecord uploadRecord = new BillingUploadRecord();
        uploadRecord.setId(id);
        uploadRecord.setTxtFileName(txtFileName);
        uploadRecord.setTxtFilePath(txtFilePath);
        uploadRecord.setAsn1FileName(asn1FileName);
        uploadRecord.setAsn1FilePath(asn1FilePath);
        uploadRecord.setStatus(UploadRecordStatusEnum.UPLOADED.getValue());
        uploadRecord.setIsEmpty(isEmpty);
        uploadRecord.setCreateTime(new Date());

        BillingFileBO.uploadFile(txtFilePath, txtInputStream);

        BillingFileBO.uploadFile(asn1FilePath, asn1InputStream);

        uploadRecordRepository.save(uploadRecord);

        return new BusinessBO<BillingUploadRecord>(uploadRecord, true, null, null);
    }

    @Override
    public BusinessBO<BillingUploadRecord> received(String id) {
        BillingUploadRecord uploadRecord = uploadRecordRepository.getById(id);
        uploadRecord.setStatus(UploadRecordStatusEnum.RECEIVED.getValue());

        uploadRecordRepository.save(uploadRecord);
        return new BusinessBO<BillingUploadRecord>(uploadRecord, true, null, null);
    }

    @Override
    public BusinessBO<BillingUploadAnswerRecord> errorFileUpload(String errorFileName, InputStream fileInputStream) {
        Iterable<String> splitterIt = Splitter.on("_").split(errorFileName);
        String fileType = Iterables.get(splitterIt, 0).substring(0, 1);
        String yyyyMMdd = Iterables.getLast(splitterIt).substring(0, 8);
        String errorFilePath = "/error/" + yyyyMMdd + "/" + errorFileName;
        Integer answerType;
        if ("E".equals(fileType)) {
            answerType = AnswerTypeEnum.ERROR.getValue();
        } else if ("F".equals(fileType)) {
            answerType = AnswerTypeEnum.REFUSE.getValue();
        } else {
            answerType = AnswerTypeEnum.OTHER.getValue();
        }

        BillingUploadAnswerRecord answerRecord = new BillingUploadAnswerRecord();
        answerRecord.setId(UUIDUtils.getUUID());
        answerRecord.setFileName(errorFileName);
        answerRecord.setAnswerType(answerType);
        answerRecord.setFilePath(errorFilePath);
        answerRecord.setCreateTime(new Date());

        answerRecordRepository.save(answerRecord);

        BillingFileBO.uploadFile(errorFilePath, fileInputStream);

        return new BusinessBO<BillingUploadAnswerRecord>(null, true, null, null);
    }

    @Override
    public PageInfo<BillingUploadRecord> getUploadFile(BillingUploadRecord uploadRecordBO, int currentPage, int pageSize) {

        Query query = new Query();
        if (!Strings.isNullOrEmpty(uploadRecordBO.getId())) {
            query.addCriteria(Criteria.where("id").is(uploadRecordBO.getId()));
        }

        if (!Strings.isNullOrEmpty(uploadRecordBO.getAsn1FileName())) {
            query.addCriteria(Criteria.where("asn1FileName").is(uploadRecordBO.getAsn1FileName()));
        }

        if (uploadRecordBO.getStatus() != null) {
            query.addCriteria(Criteria.where("status").is(uploadRecordBO.getStatus()));
        }

        if (uploadRecordBO.getIsEmpty() != null) {
            query.addCriteria(Criteria.where("isEmpty").is(uploadRecordBO.getIsEmpty()));
        }

        if (uploadRecordBO.getIsError() != null) {
            query.addCriteria(Criteria.where("isError").is(uploadRecordBO.getIsError()));
        }

        long count = mongoTemplate.count(query, BillingUploadRecord.class);
        PageInfo<BillingUploadRecord> pageInfo = new PageInfo<BillingUploadRecord>();
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(currentPage);
        if (count > 0) {
            query.skip((currentPage - 1) * pageSize).limit(pageSize);
            query.with(new Sort(Sort.Direction.DESC, "createTime"));
            List<BillingUploadRecord> records = mongoTemplate.find(query, BillingUploadRecord.class);
            pageInfo.setList(records);
        } else {

        }
        return pageInfo;
    }

    @Override
    public PageInfo<BillingUploadAnswerRecord> getAnswerFile(BillingUploadAnswerRecord uploadAnswerRecordBO, int currentPage, int pageSize) {

        Query query = new Query();
        if (!Strings.isNullOrEmpty(uploadAnswerRecordBO.getId())) {
            query.addCriteria(Criteria.where("id").is(uploadAnswerRecordBO.getId()));
        }

        if (!Strings.isNullOrEmpty(uploadAnswerRecordBO.getFileName())) {
            query.addCriteria(Criteria.where("fileName").is(uploadAnswerRecordBO.getFileName()));
        }

        if (uploadAnswerRecordBO.getAnswerType() != null) {
            query.addCriteria(Criteria.where("answerType").is(uploadAnswerRecordBO.getAnswerType()));
        }

        long count = mongoTemplate.count(query, BillingUploadAnswerRecord.class);
        PageInfo<BillingUploadAnswerRecord> pageInfo = new PageInfo<BillingUploadAnswerRecord>();
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageSize);
        pageInfo.setPageNum(currentPage);
        if (count > 0) {
            query.skip((currentPage - 1) * pageSize).limit(pageSize);
            query.with(new Sort(Sort.Direction.DESC, "createTime"));
            List<BillingUploadAnswerRecord> records = mongoTemplate.find(query, BillingUploadAnswerRecord.class);
            pageInfo.setList(records);
        } else {

        }
        return pageInfo;
    }

    @Override
    public byte[] downloadFtpFile(String filePath) {
        return BillingFileBO.downloadFtpFile(filePath);
    }
    
    @Override
    public InputStream download(String filePath) {
        return BillingFileBO.download(filePath);
    }

}
