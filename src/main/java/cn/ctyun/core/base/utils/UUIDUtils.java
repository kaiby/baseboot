package cn.ctyun.core.base.utils;

import java.util.UUID;

import org.springframework.util.StringUtils;

/**
 * Created by zhouxiaobo on 2017/6/1.
 */
public class UUIDUtils {

	public UUIDUtils() {
	}

	public static synchronized String getUUID() {
		UUID uuid = UUID.randomUUID();
		return StringUtils.replace(uuid.toString().trim(), "-", "");
	}
}
