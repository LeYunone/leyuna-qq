package com.zy.qq.seriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zy.qq.Exception.UsernameException;
import com.zy.qq.sql.Conn;

/**
 * 注册  邮箱 数据库操作
 * @author 清风理辛
 *
 */
public class SQLSendemail {

	public void regUser(String username,String password) throws UsernameException,SQLException{
		Connection conn=null;
		ResultSet rs=null;
		System.out.println(username);
		System.out.println(password);
		try{
			conn=Conn.getConn();
			PreparedStatement ps = conn.prepareStatement("select * from userinfo where uiemail=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				throw new UsernameException();
			}
			ps = conn.prepareStatement("insert into userinfo(uiid,uiemail,uipassword,createtime,uisign,uihead) values(?,?,?,sysdate,?,?)");
			String uiid=System.currentTimeMillis() + "R" + (int) (Math.random() * 1000);
			ps.setString(1, uiid);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.setString(4, "这个人太懒");
			ps.setString(5, "1");
			System.out.println(uiid+"===="+username+"==="+password);
			int num=ps.executeUpdate();
			if (num <= 0) {
				throw new SQLException();
			}
			
//			ps=conn.prepareStatement("insert into hy(hyid,uiid,hyuid) values(?,?,?)");  添加自己  废除！
//			ps.setString(1, uiid);
//			ps.setString(2, uiid);
//			ps.setString(3, uiid);
//			ps.executeUpdate();
			
			ps = conn.prepareStatement("insert into ps(psid,pgid,uiid) values(?,?,?)");
			ps.setString(1, System.currentTimeMillis()+"R");
			System.out.println(1);
			ps.setString(2, "3");
			System.out.println(2);
			ps.setString(3, uiid);
			ps.executeUpdate();
		}catch (SQLException e) {
			throw e;
		}finally{
			conn.close();
		}
		
	}
}
