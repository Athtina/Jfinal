package com.example.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Environment帮助类，获取spring加载的配置项
 * 
 * @author wenbin.dai
 *
 */
public class EnvironmentHelper {

	private static Environment environment;

	public static String getProperty(String key) {
		return environment.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return environment.getProperty(key, defaultValue);
	}

	public static <T> T getProperty(String key, Class<T> targetType) {
		return environment.getProperty(key, targetType);
	}

	public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return environment.getProperty(key, targetType, defaultValue);
	}

	public static Environment getEnvironment() {
		return environment;
	}

	@Configuration
	public static class EnvironmentConfig implements EnvironmentAware {
		@Override
		public void setEnvironment(Environment environment) {
			EnvironmentHelper.environment = environment;
		}
	}

}
