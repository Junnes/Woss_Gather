package com.briup.main;

import java.util.Collection;

import com.briup.common.Configuration;
import com.briup.server.DBStoreImpl;
import com.briup.server.ServerImpl;
import com.briup.util.BIDR;

/**
 * 服务器端
 */
public class ServerMain {
	public static void main(String[] args) {
		System.out.println("这是服务器：  ");
		try {
			/*
			 * Collection<BIDR> list = new ServerImpl().revicer(); new
			 * DBStoreImpl().saveToDB(list);
			 */
			Configuration conf = new Configuration();
			Collection<BIDR> list = conf.getServer().revicer();
			conf.getDBStore().saveToDB(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
