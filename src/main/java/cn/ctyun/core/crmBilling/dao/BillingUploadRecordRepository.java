package cn.ctyun.core.crmBilling.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cn.ctyun.core.crmBilling.entity.BillingUploadRecord;

public interface BillingUploadRecordRepository extends MongoRepository<BillingUploadRecord, String> {

    public BillingUploadRecord getById(String id);

}
