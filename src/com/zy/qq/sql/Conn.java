package com.zy.qq.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * �������ݿ�
 * @author �������
 *
 */
public class Conn {

	static{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn(){
		Connection conn=null;
		
		try {
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","mysql","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
