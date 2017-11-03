package com.briup.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.briup.common.BackUpImpl;
import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.woss.client.Gather;

/**
 * 完成数据采集，封装成一个装有BIDR对象的集合
 */

public class GatherImpl implements Gather {
	@Override
	public void init(Properties arg0) {

	}

	/*
	 * 采集
	 */

	public Collection<BIDR> gather() throws Exception {
		FileReader reader = new FileReader("src/com/briup/file/radwtmp");
		BufferedReader br = new BufferedReader(reader);
		// 遍历找出
		Map<String, Object> map = new HashMap<String, Object>();
		List list = new ArrayList<>();
		String line = null;
		// 判断是否读到最后一行
		while ((line = br.readLine()) != null) {
			// #briup1660|037:wKgB1660A|7|1239110900|44.211.221.247
			String[] string = line.split("\\|");
			String user = string[0].substring(0); // 用户名截取
			String nsp_ip = string[1];
			String n = string[2];
			String time = string[3];
			String ip = string[4];
			// 判断一下 上线 /下线
			if (n.equals("7")) {
				BIDR bidr = new BIDR();
				bidr.setAAA_login_name(user);
				bidr.setLogin_ip(nsp_ip);
				// 转换为Timestamp类型
				Timestamp log_time = new Timestamp(Long.parseLong(time));
				bidr.setLogin_date(log_time);
				bidr.setLogin_ip(ip);
				// 保存第一次信息
				if (!map.containsKey(ip)) {
					map.put(ip, bidr);
				}
			} else if (n.equals("8")) {
				BIDR bidr = (BIDR) map.get(ip);
				Timestamp login_time = bidr.getLogin_date();// 获取上线时间
				Timestamp logout_time = new Timestamp(Long.parseLong(time));// 获取下线时间
				int time_deration = (int) (logout_time.getTime() - login_time
						.getTime());
				// 获取时长
				bidr.setTime_deration(time_deration);
				list.add(bidr);
				map.remove(n);// 拿走7 / 8
			}
		}
		/*
		 * for (Object object : list) { System.out.println(object); }
		 */
		System.out.println("客户端采集完成" + list.size());
		new BackUpImpl().store("第一次备份", map, true);
		new LoggerImpl().warn("warn");
		return list;
	}
}
