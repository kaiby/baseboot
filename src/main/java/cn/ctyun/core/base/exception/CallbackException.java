package cn.ctyun.core.base.exception;

/**
 * Created by zhouxiaobo on 2017/8/23. 回调异常类
 */
public class CallbackException extends Exception {

	private static final long serialVersionUID = 1L;

	public CallbackException() {
		super();
	}

	public CallbackException(String message) {
		super(message);
	}
}
