package com.briup.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Properties;



import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.woss.server.DBStore;

/**
 * 执行入库操作
 */
public class DBStoreImpl implements DBStore {
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static ResultSet resultSet;
	private static PreparedStatement ps = null;
	private static Connection connection = null;
	@Override
	public void init(Properties arg0) {
	}

	/*
	 * 入库
	 */
	@Override
	public void saveToDB(Collection<BIDR> arg0) throws Exception {
	  driver="oracle.jdbc.driver.OracleDriver";
	  url="jdbc:oracle:thin:@localhost:1521:XE";
	  username="pig";
	  password="pig";

		Class.forName(driver);
		// 2、建立连接
		/*
		 * 第一个参数：指定数据库的url标识 第二个参数，数据库的用户名 第三个参数，数据库的密码
		 */
		connection = DriverManager.getConnection(url,username,password);
		//设置手动提交
		connection.setAutoCommit(false);
		//3.创建statement对象
		List<BIDR> list = (List<BIDR>) arg0;
		//为了防止ps对象出现最大游标溢出问题，需要将ps对象实时关闭
		for (int i = 0; i < list.size(); i++) {
			BIDR bidr = list.get(i);
			int day = (int) bidr.getLogin_date().getDate();
			String sql = "insert into t_detail_" + day + " values(?,?,?,?,?,?)";
			/*
			 * 调用日志文件
			 */
			//new LoggerImpl().warn(sql);
			ps = connection.prepareStatement(sql);
			/*
			 * 完善SQL语句 第一个参数：SQL语句中第几个？ index从1开始 第二个参数：要传入的值， 要与set方法的数据类型匹配
			 */
			ps.setString(1, bidr.getAAA_login_name());
			ps.setString(2, bidr.getLogin_ip());
			ps.setTimestamp(3, bidr.getLogin_date());
			ps.setTimestamp(4, bidr.getLogout_date());
			ps.setString(5, bidr.getNAS_ip());
			ps.setInt(6, bidr.getTime_deration()/1000/60);	
			ps.executeUpdate();
			connection.commit();
			ps.close();
			
			/*  //执行SQL语句 
			 resultSet = ps.executeQuery(); //处理结果集
			  while(resultSet.next()){
			  System.out.print("AAA_login_name:"+resultSet
			  .getString("AAA_login_name")
			  +" Login_ip:"+resultSet.getString("Login_ip")
			  +" Login_date:"+resultSet.getTimestamp("Login_date")
			  +" Logout_date:"+resultSet.getTimestamp("Logout_date")
			  +" NAS_ip:"+resultSet.getString("NAS_ip")
			  +" Time_deration:"+resultSet.getInt("Time_deration")); }
			*/	 
		}
		if(connection!=null){
			connection.close();
		}
		System.out.println("数据入库完成！");
		
	}

}
