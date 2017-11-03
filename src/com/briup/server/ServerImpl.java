package com.briup.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.server.Server;

/**
 * 中央服务器
 */
public class ServerImpl implements Server {
	private static ServerSocket ss = null;
	private static int port;

	@Override
	public void init(Properties p) {
		port = 2442;
	}

	@Override
	public Collection<BIDR> revicer() throws Exception {
		// 接收来自多个客户端发来的信息
		ss = new ServerSocket(port);
		Socket s = ss.accept();
		InputStream is = s.getInputStream();
		// 发送的是List集合对象/用对象流
		ObjectInputStream ois = new ObjectInputStream(is);
		// Collection<BIDR> bidr = (Collection) ois.readObject();
		List<BIDR> bidr = (List) ois.readObject();
		ois.close();
		System.out.println("服务器全已经接收完成！");
		return bidr;
	}

	@Override
	// 关闭
	public void shutdown() {
		if (ss != null) {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
