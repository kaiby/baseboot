package com.peazone.core.common.constant;

public enum PromiseEnum {
	/**
	 * 无承诺函
	 */
	NO(0),

	/**
	 * 有承诺函
	 */
	YES(1);

	private int value;

	PromiseEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
