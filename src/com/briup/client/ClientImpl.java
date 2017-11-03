package com.briup.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.client.Client;

/**
 * AAA服务器客户端
 */
public class ClientImpl implements Client {
	private static String ip;
	private static int port;

	@Override
	public void init(Properties p) {
		ip = "127.0.0.1";
		port = 2442;
	}

	/*
	 * 向服务器发送采集好的数据
	 */
	@Override
	public void send(Collection<BIDR> arg0) throws Exception {

		Socket s = new Socket(ip, port);
		OutputStream os = s.getOutputStream();
		// 发送的是List集合对象/用对象流
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(arg0);
		oos.flush();
		oos.close();
		System.out.println("客户端已经发送成功！");
		if (s != null) {
			s.close();
		}
	}

}
