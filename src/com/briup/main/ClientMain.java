package com.briup.main;

import java.util.Collection;

import org.apache.log4j.PropertyConfigurator;

import com.briup.client.ClientImpl;
import com.briup.client.GatherImpl;
import com.briup.common.Configuration;
import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.woss.client.Gather;

/**
 * 客户端
 */
public class ClientMain {
	public static void main(String[] args) {
		System.out.println("这是客户端。。。");
		try {
			/*ClientImpl c = new ClientImpl();
			Collection<BIDR> list = new GatherImpl().gather();
			c.send(list);*/
			
			
			Configuration conf = new Configuration();
			Collection<BIDR> list = conf.getGather().gather();
			System.out.println(list.size());
			conf.getClient().send(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
