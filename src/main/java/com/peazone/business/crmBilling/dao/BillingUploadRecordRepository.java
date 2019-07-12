package com.peazone.business.crmBilling.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.peazone.business.crmBilling.entity.BillingUploadRecord;

public interface BillingUploadRecordRepository extends MongoRepository<BillingUploadRecord, String> {

    public BillingUploadRecord getById(String id);

}
