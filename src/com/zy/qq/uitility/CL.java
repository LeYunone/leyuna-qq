package com.zy.qq.uitility;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import com.zy.qq.View.ChatView;
import com.zy.qq.View.GroupChatView;
import com.zy.qq.client.CreateUser;
import com.zy.qq.client.CreateUserList;
import com.zy.qq.client.CreateUserList2;
import com.zy.qq.client.FriendList;
import com.zy.qq.client.Friends;
import com.zy.qq.client.Group;
import com.zy.qq.client.GroupList;
import com.zy.qq.client.GroupUser;
import com.zy.qq.client.GroupUserList;
import com.zy.qq.client.Userinfo;
import com.zy.qq.client.UserinfoList;

/**
 * 存储常量
 * @author 清风理辛
 *
 */
public class CL {

	public static final int Login_port=4001;   //登录服务器用的端口
	public static final int chat_port=4002;   //聊天服务器用的端口
	public static final int reg_port=4003;    //注册服务器用的端口
	public static final int Add_port=4007;    //添加服务器用的端口
	public static final int File_port=4009;  //文件服务器用的端口
	
	public static Hashtable<String, String> fileMap=new Hashtable<String, String>();  //服务器用  存储文件编号
	public static String fileTouid="";
	public static String filename="";  //客户端用
	public static String filenameserver=""; //服务器用
	
	public static String username="";  //账号
	public static String password="";  //密码
	public static final String ip="127.0.0.1";  //服务器ip
	
	
	public static String json_friend="";  //json 格式的好友资料
//	public static String friend_info="";  //解析json后的好友资料
	public static String friend_list="";  //好友列表 xxx,xxx,xxx 仅包含好友编号    解析json后的好友资料     用于判断好友是否在线
	public static String friend_online="";  //所有在线好友   StringBuffere 格式  xxx,xxx,xxx  仅有编号的格式
	public static FriendList friendlist=null;   //好友列表  服务器用
	
	public static String My_json_info ="";   //个人资料信息
	
	public static DatagramSocket datasocket=null;  //聊天服务器用的udp客户端接口
//	public static DatagramSocket datasocket2=null; //添加好友服务器用的udp客户端接口
	
	public static Hashtable<String, ChatView> Chatmap=new Hashtable<String, ChatView>();  //将好友的编号，和与好友聊天的窗口进行登记 方便控制
	public static Hashtable<String, Friends> map=new Hashtable<String, Friends>();   //记录好友数目
	
	public static String json_All_userinfo ="";  //数据库里所有的用户信息表 jsonarray 类型
	public static String All_userinfo_online="";  //软件中所有在线用户的id
	public static Hashtable<String, Userinfo> Allusermap=new Hashtable<String, Userinfo>();  //记录所有用户的数目以及信息
	public static UserinfoList userinfolist=null;
	
	public static String stringbuffer_list_myGroup="";  //我的所有群的编号
	public static String json_list_myGroupinfo="";    //我加入的群的基本信息
	public static String Map_User_Group="";  //Json 格式下的map  取值为json.getString （群编号）
	public static String Map_User_Group_Online="" ;  //json 格式下的map   为在线用户的编号
	public static Hashtable<String, ArrayList<GroupUser>> group_user_map=new Hashtable<String , ArrayList<GroupUser>>();  //面向对象的存储群下的用户
	public static Vector<GroupUserList> groupUserviewlist=new Vector<GroupUserList>();  //记录群聊天窗口
	public static Hashtable<String, GroupUser> groupuserMap=new Hashtable<String, GroupUser>(); //记录用户数目
	
	
	public static Hashtable<String, Group > groupMap=new Hashtable<String, Group>();   //面向对象 存储群对象
	public static Hashtable<String, GroupChatView> chatsmap=new Hashtable<String, GroupChatView>();  //存储群聊天板
	
	public static GroupList grouplist=null;  //群列表
	public static Hashtable<String, CreateUser> Create_Group_User_map=new Hashtable<String, CreateUser>();  //创建群聊时下面的用户对象存储
	public static Hashtable<String, CreateUser> Create_Group_User_map2=new Hashtable<String, CreateUser>();  //创建群聊时确定的用户 
	public static CreateUserList2 createu2=null;
	public static CreateUserList createu=null;
	
 	
}
