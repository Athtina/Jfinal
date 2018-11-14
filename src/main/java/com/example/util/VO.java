package com.example.util;

import java.util.Map;

/**
 * 业务参数VO 定义通用方法
 * 
 * @author wenbin.dai
 *
 */
public interface VO {

	/**
	 * 转为json字符串
	 * 
	 * @return String
	 */
	default String toJson() {
		return JsonHelper.parseToJson(this);
	}

	/**
	 * 将简单对象转为表单提交的参数
	 * 
	 * @return Map<String, String>
	 */
	default Map<String, String> toStringMap() {
		return JsonHelper.parseToMapStrStr(toJson());
	}

}
