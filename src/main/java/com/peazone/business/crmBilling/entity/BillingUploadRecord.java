package com.peazone.business.crmBilling.entity;

import java.util.Date;

public class BillingUploadRecord {

    private String id;
    private String txtFileName;
    private String asn1FileName;
    private String txtFilePath;
    private String asn1FilePath;
    private Integer status;
    private Integer isEmpty;
    private Integer isError;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxtFileName() {
        return txtFileName;
    }

    public void setTxtFileName(String txtFileName) {
        this.txtFileName = txtFileName;
    }

    public String getAsn1FileName() {
        return asn1FileName;
    }

    public void setAsn1FileName(String asn1FileName) {
        this.asn1FileName = asn1FileName;
    }

    public String getTxtFilePath() {
        return txtFilePath;
    }

    public void setTxtFilePath(String txtFilePath) {
        this.txtFilePath = txtFilePath;
    }

    public String getAsn1FilePath() {
        return asn1FilePath;
    }

    public void setAsn1FilePath(String asn1FilePath) {
        this.asn1FilePath = asn1FilePath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
