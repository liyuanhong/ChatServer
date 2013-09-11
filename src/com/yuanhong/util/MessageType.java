package com.yuanhong.util;

public class MessageType {
	public static int DEFAULT = 0;    //默认的发送消息的形式
	public static int LOGOUT = 2;     //某用户退出时，向在线用户发送用户列表更新消息
	public static int LOGIN = 3;      //当某用户登录时，向在线用户发送用户列表更新消息
	public static int SHUTDOWN = 4;   //当服务器关闭时，向所有在线用户发送提示消息
}
