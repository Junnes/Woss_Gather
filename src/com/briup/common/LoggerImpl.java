package com.briup.common;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

/**
 * 日志模块
 */
public class LoggerImpl implements com.briup.util.Logger{//加包名
	static{
		String path = "src/com/briup/file/log4j.properties";
		//读取使用Java的特性文件编写的配置文件
		PropertyConfigurator.configure(path);   
	}
	@Override
	public void init(Properties arg0) {
		
	}

	@Override
	public void debug(String arg0) {
		Logger.getLogger("mylogger").debug(arg0);
	}

	@Override
	public void error(String arg0) {
		Logger.getLogger("mylogger").error(arg0);
	}

	@Override
	public void fatal(String arg0) {
		Logger.getLogger("mylogger").fatal(arg0);
	}

	@Override
	public void info(String arg0) {
		Logger.getLogger("mylogger").info(arg0);
	}

	@Override
	public void warn(String arg0) {
		Logger.getLogger("mylogger").warn(arg0);
	}

}
