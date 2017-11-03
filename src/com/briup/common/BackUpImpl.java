package com.briup.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.BackUP;

/**
 * 备份模块
 */
public class BackUpImpl implements BackUP {
	private String path = "src/a.txt";
	@Override
	public void init(Properties arg0) {
		
	}
	// 读取
	@Override
	public Object load(String arg0, boolean arg1) throws Exception {
		// ip,判断
		/*
		 * File file = new File(path); if (!file.exists()) { return null; }
		 */
		if (arg1) {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Map<String, BIDR> map = (Map<String, BIDR>) ois.readObject();
			for (String key : map.keySet()) {
				if (key.equals(arg0)) {
					return map.get(key);
				}
			}
		}
		return null;
	}
	// 存储
	@Override
	public void store(String arg0, Object arg1, boolean arg2) throws Exception {
		// 描述信息
		// 备份的对象
		// 标识符 /_\为true就是在文件末尾追加内容，为false就是覆盖。
		// File file = new File(path);
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(arg1);
		oos.flush();
		System.out.println("备份完成");
		oos.close();
	}
}
