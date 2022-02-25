package com.zy.qq.seriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import net.sf.json.JSONObject;
import com.zy.qq.Exception.AccountException;
import com.zy.qq.Exception.NotUserException;
import com.zy.qq.Exception.PasswordException;
import com.zy.qq.sql.Conn;

/**
 * ������ִ��sql���
 * @author �������
 *
 */
public class IFLogin {

	public static String loginemail(String username,String password) throws NotUserException, PasswordException, AccountException, SQLException{
		return login(username,password,"select * from userinfo where uiemail=?");
	}
	
	public static String loginiphone(String username,String password) throws NotUserException, PasswordException, AccountException, SQLException{
		return login(username,password,"select * from userinfo where uiiphone=?");
	}
	
	public static String login(String username,String password,String sql) throws NotUserException,PasswordException,AccountException,SQLException{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){  //��������ݴ���
				if(rs.getInt("state")==0){   //����˻���Ȩ�޵�¼
					
					if(rs.getString("uipassword").equals(password)){ //�����������ݿ��е��������
//						System.out.println("�ɹ�");  ������
//						System.out.println(rs.getString("uiid"));
						return rs.getString("uiid");  //�����û����
					}else{
						throw new PasswordException();  //�������
					}
				}else{
					throw new AccountException();   //�˻���������
				}
			}else{
				throw new NotUserException();    //û�д��û�
			}
		} catch (SQLException e) {
			throw e;        //���ݿ��������
		}finally{
			conn.close();
		}
	}
	
	public static Vector<Friend> getFriend(String uid) throws SQLException{
		Vector<Friend> vc=new Vector<Friend>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		try {
//			ps=conn.prepareStatement("select u.uiid,u.uinetname,u.uisign from userinfo u left join hy on u.uiid=hy.hyuid and u.uiid=?");
			ps=conn.prepareStatement("select u.uiemail, u.uiid,u.uihead,u.uinetname,u.uisign from userinfo u where u.uiid in (select hy.hyuid from hy,userinfo where userinfo.uiid=? and userinfo.uiid=hy.uiid)");
			ps.setString(1, uid);		
			rs=ps.executeQuery();

			while(rs.next()){
				Friend f=new Friend();
				f.setEmail(rs.getString("uiemail"));
				f.setUid(rs.getString("uiid"));
				f.setNetname(rs.getString("uinetname"));
				f.setHead(rs.getString("uihead"));
				f.setSign(rs.getString("uisign"));
				vc.add(f);
			}
			return vc;
		}catch (SQLException e) {
			throw e;
		}finally{
			conn.close();
		}
	}
	
	public static Myuserinfo reMyuser(String uid){   //�õ������û����е���Ϣ
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		Myuserinfo my=new Myuserinfo();
		
		try {
			ps=conn.prepareStatement("select * from userinfo where uiid=? ");
			ps.setString(1, uid);
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				String netname=rs.getString("uinetname");
				String head= rs.getString("uihead");
				String sign=rs.getString("uisign");
				String blood=rs.getString("uiblood");
				String day=rs.getString("uiday");
				String email=rs.getString("uiemail");
				String iphone=rs.getString("uiiphone");
				String month=rs.getString("uimonth");
				String name=rs.getString("uiname");
				String school=rs.getString("uischool");
				String sex=rs.getString("uisex");
				String year=rs.getString("uiyear");
				my.setHead(head);
				my.setNetname(netname);
				my.setSign(sign);
				my.setUid(uid);
				my.setBlood(blood);
				my.setDay(day);
				my.setEmail(email);
				my.setIphone(iphone);
				my.setMonth(month);
				my.setName(name);
				my.setSchool(school);
				my.setSex(sex);
				my.setYear(year);
			}
			return my;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return my;
	}
	private static Vector<Friend> friends=null;
	public static Vector<Friend> queryAll(){   //��ѯ���е��û�
		friends=new Vector<Friend>();
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		try {
			ps=conn.prepareStatement("select * from userinfo ");
			rs=ps.executeQuery();
			while(rs.next()){
				Friend f=new Friend();
				String email=rs.getString("uiemail");
				String netname=rs.getString("uinetname");
				String head= rs.getString("uihead");
				String sign=rs.getString("uisign");
				String uid=rs.getString("uiid");
				f.setHead(head);
				f.setEmail(email);
				f.setNetname(netname);
				f.setSign(sign);
				f.setUid(uid);
				friends.add(f);
			}
			return friends;
		}catch (Exception e) {
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return friends;
	}
	
	public static Friend getUserinfo(String uid){  //��ѯ��Ӧid���û���Ϣ
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		Friend f=new Friend();
		try {
			ps=conn.prepareStatement("select * from userinfo where uiid=? ");
			ps.setString(1, uid);
			rs=ps.executeQuery();
			while(rs.next()){
				String netname=rs.getString("uinetname");
				String head= rs.getString("uihead");
				String sign=rs.getString("uisign");
				f.setHead(head);
				f.setNetname(netname);
				f.setSign(sign);
				f.setUid(uid);
			}
			return f;
		}catch (Exception e) {
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	public static int addFriend(String uid,String touid){  //��Ӻ���
		Connection conn=null;
		PreparedStatement ps=null;
		conn=Conn.getConn();
		
		int num=0;
		int num2=0;
		
		try {
			String hyid=System.currentTimeMillis() + "R" + (int) (Math.random() * 1000);
			ps=conn.prepareStatement("insert into hy(hyid,uiid,hyuid,createtime) values(?,?,?,sysdate) ");
			ps.setString(1, hyid);
			ps.setString(2, uid);
			ps.setString(3, touid);
			System.out.println(uid+"==="+touid);
			ps.executeUpdate();
			
			String hyid2=System.currentTimeMillis() + "R" + (int) (Math.random() * 1000);
			ps=conn.prepareStatement("insert into hy(hyid,uiid,hyuid,createtime) values(?,?,?,sysdate) ");
			ps.setString(1, hyid2);
			ps.setString(2, touid); 
			ps.setString(3, uid);
			System.out.println(touid+"==="+uid);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return num+num2;
	}
	
	public static HashMap<String,Group> getGroup(String...str){  //�õ����е�Ⱥ���    �Լ�Ⱥ���������������û����                                                                                                                                                                                                                                                                                                                         
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		HashMap<String,Group> map=new HashMap<String, Group>();  //�洢Ⱥ���
		try {
			if(str.length==0){
				ps=conn.prepareStatement("select * from ps ");
				rs=ps.executeQuery();
				while(rs.next()){
					Set<String> set=map.keySet();
					String pid=rs.getString("pgid");
					String uid=rs.getString("uiid");
					if(map.get(pid)==null){  //������ڿ�
						Group group=new Group();
						group.setPid(pid);
						group.add(uid);
						map.put(pid, group);
					}else{   //��������ڿ�
						map.get(pid).add(uid);  //���ÿ��Ⱥ��Ӧһ��Ⱥ��  ÿһ��Ⱥ������һ��list���� �洢�� ���е��û� ���
					}
				}	
			}else {
				for(String pgid:str){
					ps=conn.prepareStatement("select * from ps where pgid=? ");
					ps.setString(1, pgid);
					rs=ps.executeQuery();
					while(rs.next()){
						Set<String> set=map.keySet();
						String pid=rs.getString("pgid");
						String uid=rs.getString("uiid");
						if(map.get(pid)==null){  //������ڿ�
							Group group=new Group();
							group.setPid(pid);
							group.add(uid);
							map.put(pid, group);
						}else{   //��������ڿ�
							map.get(pid).add(uid);  //���ÿ��Ⱥ��Ӧһ��Ⱥ��  ÿһ��Ⱥ������һ��list���� �洢�� ���е��û� ���
						}
					}
				}	
			}
			return map;
		}catch (Exception e) {
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static StringBuffer getMyGroup(String uid){  //��ѯ�Ҽ����Ⱥ���
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		StringBuffer sb=new StringBuffer();  //�洢Ⱥ���
		try {
			ps=conn.prepareStatement("select * from ps where uiid=? ");
			ps.setString(1, uid);
			rs=ps.executeQuery();
			while(rs.next()){
				String pid=rs.getString("pgid"); //�õ��ҵ�Ⱥ���
				sb.append(pid);
				sb.append(",");
			}
			return sb;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("������ ִ��06����ʧ�� ");
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sb;
	}
	
	public static Vector<Groupinfo> getMyGroupinfo(String [] str){  //��ѯ�Ҽ����Ⱥ�Ļ�����Ϣ
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		conn=Conn.getConn();
		Vector<Groupinfo> list=new Vector<Groupinfo>();  //�洢Ⱥ���
		try {
			for(int i=0;i<str.length;i++){  //������ѯȺ��Ϣ
				ps=conn.prepareStatement("select * from pgroup where pgid=? ");
				ps.setString(1, str[i]);
				rs=ps.executeQuery();
				while(rs.next()){
					Groupinfo groupinfo=new Groupinfo();
					String pid=rs.getString("pgid");
					String name=rs.getString("pgname");
					String head=rs.getString("pghead");
					String pguser=rs.getString("pguser");
					groupinfo.setHead(head);
					groupinfo.setName(name);
					groupinfo.setPid(pid);
					groupinfo.setPguser(pguser);
					list.add(groupinfo);
				}
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;	
	}
	
	public static boolean createGroup(String pgname,String pguser,String...uid){ // ����Ⱥ������Ա���
		Connection conn=null;
		PreparedStatement ps=null;
		conn=Conn.getConn();
		String pgid=System.currentTimeMillis() + "R" + (int) (Math.random() * 1000);
		try {
			ps=conn.prepareStatement("insert into pgroup (pgid,pgname,pgtime,pguser) values(?,?,sysdate,?)");
			ps.setString(1, pgid);
			ps.setString(2, pgname);
			ps.setString(3, pguser); //Ⱥ��
			int num=ps.executeUpdate();
			System.out.println(num);
			
			ps=conn.prepareStatement("insert into ps(psid,pgid,uiid) values(?,?,?) ");  //�����Ա
			for(int i=0;i<uid.length;i++){  //����������
				String psid=System.currentTimeMillis() + "R" + (int) (Math.random() * 1000);
				ps.setString(1,psid);
				ps.setString(2, pgid);
				ps.setString(3, uid[i]);
				ps.addBatch();
			}
			int s[]=ps.executeBatch();
			for(int d: s){
				System.out.print(d);
				System.out.print("====");
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static void saveUserinfo(JSONObject json){  //���������Ϣ  �õ�json��ʽ�ĸ�������
		Connection conn=null;
		PreparedStatement ps=null;
		System.out.println("���뱣��");
		System.out.println(json);
		try {
			conn=Conn.getConn();
			ps=conn.prepareStatement("update userinfo set uiemail =?,uiiphone =?,uinetname=?,uiname=?,uisign=?,uihead=?,uiyear=?,uimonth=?, uiday=? , uischool=?,uisex=?,uiblood=? where uiid=? ");
			
			ps.setString(1, json.getString("email"));
			ps.setString(2, json.getString("iphone"));
			ps.setString(3, json.getString("netname"));
			ps.setString(4, json.getString("name"));
			ps.setString(5, json.getString("sign"));
			ps.setString(6, json.getString("head"));
			ps.setString(7, json.getString("year"));
			ps.setString(8, json.getString("month"));
			ps.setString(9, json.getString("day"));
			ps.setString(10, json.getString("school"));
			ps.setString(11, json.getString("sex"));
			ps.setString(12, json.getString("blood"));
			ps.setString(13, json.getString("uid"));
			System.out.println(ps.executeUpdate());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteFriends(String myuid,String touid){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=Conn.getConn();
			ps=conn.prepareStatement("delete from hy where uiid=? and hyuid=?");
			ps.setString(1, myuid);
			ps.setString(2, touid);
			ps.executeUpdate();
			
			ps=conn.prepareStatement("delete from hy where uiid=? and hyuid=?");
			ps.setString(1, touid);
			ps.setString(2, myuid);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteGroup(String pid,String myuid){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=Conn.getConn();
			ps=conn.prepareStatement("delete from ps where uiid=? and pgid=?");
			ps.setString(1, myuid);
			ps.setString(2, pid);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void droupGroup(String pid){
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=Conn.getConn();
			ps=conn.prepareStatement("delete from ps where pgid=?");
			ps.setString(1, pid);
			ps.executeUpdate();
			ps=conn.prepareStatement("delete from pgroup where pgid=?");
			ps.setString(1, pid);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
