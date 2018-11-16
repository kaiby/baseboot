package cn.ctyun.core.base.exception;

/**
 * Created by zhouxiaobo on 2017/8/23. 参数异常类
 */
public class ParameterException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParameterException() {
		super();
	}

	public ParameterException(String message) {
		super(message);
	}
}
