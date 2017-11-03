package com.briup.common;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.util.BackUP;
import com.briup.util.Logger;
import com.briup.woss.WossModule;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

/**
 * 配置模块
 */
public class Configuration implements com.briup.util.Configuration {
	private static Properties properties;
	private static WossModule wossModule;
	private static Map<String, Object> map;
	// 解析xml文件（dom4j）
	static {
		map = new HashMap<String, Object>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read("src/com/briup/file/conf.xml");
			// 获取根节点
			Element root = document.getRootElement();
			// 遍历root获取子节点
			List<Element> list = root.elements();
			for (Element ele1 : list) {
				String className = ele1.attributeValue("class");
				// 获取标签名
				String name = ele1.getName();
				// 通过反射获取类的实例对象
				wossModule = (WossModule) Class.forName(className)
						.newInstance();
				properties = new Properties();
				// properties.put(name, list);
				// 遍历二级子标签
				List<Element> ele2 = ele1.elements();
				for (Element element : ele2) {
					// 获取标签名
					String name2 = ele1.getName();
					// 获取文本节点内容
					String value2 = element.getText();
					properties.put(name2, value2);

				}
				// 动态调用init（），
				wossModule.init(properties);
				map.put(name, wossModule);
				// 第一次遍历 gather com.briup.client.GatherImpl
				// 第二次遍历 client com.briup.client.ClientImpl
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WossModule GetModule(String name) throws Exception {
		WossModule wossModule = (WossModule) map.get(name);
		return wossModule;
	}

	@Override
	public BackUP getBackup() throws Exception {
		// return (BackUP) map.get("com.briup.common.BackUpImpl");
		BackUP module = (BackUP) GetModule("backup");
		return module;
	}

	@Override
	public Client getClient() throws Exception {
		Client client = (Client) GetModule("client");
		return client;
	}

	@Override
	public DBStore getDBStore() throws Exception {
		DBStore dbstore = (DBStore) GetModule("dbstore");
		return dbstore;
	}

	@Override
	public Gather getGather() throws Exception {
		Gather gather = (Gather) GetModule("gather");
		return gather;
	}

	@Override
	public Logger getLogger() throws Exception {
		Logger logger = (Logger) GetModule("logger");
		return logger;
	}

	@Override
	public Server getServer() throws Exception {
		// return (Server) map.get("com.briup.server.ServerImpl");
		Server server = (Server) GetModule("server");
		return server;
	}

}
