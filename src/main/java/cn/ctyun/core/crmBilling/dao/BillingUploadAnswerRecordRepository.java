package cn.ctyun.core.crmBilling.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ctyun.core.crmBilling.entity.BillingUploadAnswerRecord;

public interface BillingUploadAnswerRecordRepository extends MongoRepository<BillingUploadAnswerRecord, String> {

    public BillingUploadAnswerRecord getById(String id);

}
