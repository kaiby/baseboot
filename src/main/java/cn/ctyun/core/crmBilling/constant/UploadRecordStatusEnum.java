package cn.ctyun.core.crmBilling.constant;

public enum UploadRecordStatusEnum {
	/**
	 * 1--已生产TXT文件
	 */
    TXT_CREATED(2),
    
    /**
     * 2--已生产asn1文件
     */
    ASN1_CREATED(2),

	/**
	 * 3--已上传
	 */
	UPLOADED(3),
    
    /**
     * 4--接收成功
     */
	RECEIVED(4);

	private int value;

	UploadRecordStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
