package cn.ctyun.core.crmBilling.constant;

public enum AnswerTypeEnum {
    
    /**
     * 0--其他文件
     */
    OTHER(0),
    
	/**
	 * 1--确认文件
	 */
    CONFIRM(1),

	/**
	 * 2--话单内容有误
	 */
	ERROR(2),
    
    /**
     * 3--话单拒收
     */
    REFUSE(3);

	private int value;

	AnswerTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
