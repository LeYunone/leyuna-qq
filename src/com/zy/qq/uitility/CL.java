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
 * �洢����
 * @author �������
 *
 */
public class CL {

	public static final int Login_port=4001;   //��¼�������õĶ˿�
	public static final int chat_port=4002;   //����������õĶ˿�
	public static final int reg_port=4003;    //ע��������õĶ˿�
	public static final int Add_port=4007;    //��ӷ������õĶ˿�
	public static final int File_port=4009;  //�ļ��������õĶ˿�
	
	public static Hashtable<String, String> fileMap=new Hashtable<String, String>();  //��������  �洢�ļ����
	public static String fileTouid="";
	public static String filename="";  //�ͻ�����
	public static String filenameserver=""; //��������
	
	public static String username="";  //�˺�
	public static String password="";  //����
	public static final String ip="127.0.0.1";  //������ip
	
	
	public static String json_friend="";  //json ��ʽ�ĺ�������
//	public static String friend_info="";  //����json��ĺ�������
	public static String friend_list="";  //�����б� xxx,xxx,xxx ���������ѱ��    ����json��ĺ�������     �����жϺ����Ƿ�����
	public static String friend_online="";  //�������ߺ���   StringBuffere ��ʽ  xxx,xxx,xxx  ���б�ŵĸ�ʽ
	public static FriendList friendlist=null;   //�����б�  ��������
	
	public static String My_json_info ="";   //����������Ϣ
	
	public static DatagramSocket datasocket=null;  //����������õ�udp�ͻ��˽ӿ�
//	public static DatagramSocket datasocket2=null; //��Ӻ��ѷ������õ�udp�ͻ��˽ӿ�
	
	public static Hashtable<String, ChatView> Chatmap=new Hashtable<String, ChatView>();  //�����ѵı�ţ������������Ĵ��ڽ��еǼ� �������
	public static Hashtable<String, Friends> map=new Hashtable<String, Friends>();   //��¼������Ŀ
	
	public static String json_All_userinfo ="";  //���ݿ������е��û���Ϣ�� jsonarray ����
	public static String All_userinfo_online="";  //��������������û���id
	public static Hashtable<String, Userinfo> Allusermap=new Hashtable<String, Userinfo>();  //��¼�����û�����Ŀ�Լ���Ϣ
	public static UserinfoList userinfolist=null;
	
	public static String stringbuffer_list_myGroup="";  //�ҵ�����Ⱥ�ı��
	public static String json_list_myGroupinfo="";    //�Ҽ����Ⱥ�Ļ�����Ϣ
	public static String Map_User_Group="";  //Json ��ʽ�µ�map  ȡֵΪjson.getString ��Ⱥ��ţ�
	public static String Map_User_Group_Online="" ;  //json ��ʽ�µ�map   Ϊ�����û��ı��
	public static Hashtable<String, ArrayList<GroupUser>> group_user_map=new Hashtable<String , ArrayList<GroupUser>>();  //�������Ĵ洢Ⱥ�µ��û�
	public static Vector<GroupUserList> groupUserviewlist=new Vector<GroupUserList>();  //��¼Ⱥ���촰��
	public static Hashtable<String, GroupUser> groupuserMap=new Hashtable<String, GroupUser>(); //��¼�û���Ŀ
	
	
	public static Hashtable<String, Group > groupMap=new Hashtable<String, Group>();   //������� �洢Ⱥ����
	public static Hashtable<String, GroupChatView> chatsmap=new Hashtable<String, GroupChatView>();  //�洢Ⱥ�����
	
	public static GroupList grouplist=null;  //Ⱥ�б�
	public static Hashtable<String, CreateUser> Create_Group_User_map=new Hashtable<String, CreateUser>();  //����Ⱥ��ʱ������û�����洢
	public static Hashtable<String, CreateUser> Create_Group_User_map2=new Hashtable<String, CreateUser>();  //����Ⱥ��ʱȷ�����û� 
	public static CreateUserList2 createu2=null;
	public static CreateUserList createu=null;
	
 	
}
