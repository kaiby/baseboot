package com.peazone.business.crmBilling.constant;

/**
 * 是否空文件
 * 
 * @author kaibyx
 * @date 2018年9月13日 下午2:39:39
 */
public enum IsEmptyEnum {
    /**
     * 0--否
     */
    NO(0),

    /**
     * 1--是
     */
    YES(1);

    private int value;

    IsEmptyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
