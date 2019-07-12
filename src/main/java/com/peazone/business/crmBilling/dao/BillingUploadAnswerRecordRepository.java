package com.peazone.business.crmBilling.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.peazone.business.crmBilling.entity.BillingUploadAnswerRecord;

public interface BillingUploadAnswerRecordRepository extends MongoRepository<BillingUploadAnswerRecord, String> {

    public BillingUploadAnswerRecord getById(String id);

}
