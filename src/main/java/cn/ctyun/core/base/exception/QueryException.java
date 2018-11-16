package cn.ctyun.core.base.exception;

/**
 * Created by zhouxiaobo on 2017/8/23. 查询异常类
 */
public class QueryException extends Exception {

	private static final long serialVersionUID = 1L;

	public QueryException() {
		super();
	}

	public QueryException(String message) {
		super(message);
	}
}
