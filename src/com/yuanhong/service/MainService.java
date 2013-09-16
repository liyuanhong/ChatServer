package com.yuanhong.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextField;

import com.yuanhong.util.AnalyseMessage;
import com.yuanhong.util.MessageType;
import com.yuanhong.util.ServiceCtrol;
import com.yuanhong.util.UserInfo;

public class MainService extends Thread {
	private String infomation = "";
	private ServerSocket serSocket;
	private Socket socket;
	private InputStreamReader reader;
	private ServiceCtrol serviceCtrol;
	private JTextField portField;
	private String sendedUser;
	private JList userInfo;
	private Vector userInfoList;
	private Map<String, UserInfo> allUserMap;

	private int messType;
	private String message;
	private String userName;
	private int port;
	private String address;

	public MainService(ServiceCtrol serviceCtrol, ServerSocket serSocket,
			JTextField portField,JList userInfo,Vector userInfoList,
			Map<String, UserInfo> allUserMap) {
		this.serviceCtrol = serviceCtrol;
		this.serSocket = serSocket;
		this.portField = portField;
		this.userInfo = userInfo;
		this.userInfoList = userInfoList;
		this.allUserMap = allUserMap;
	}

	@Override
	public void run() {
		int len;
		char[] ch = new char[512];
		while (serviceCtrol.getCtrol() == 0) {
			infomation = "";
			try {
				socket = serSocket.accept();
				reader = new InputStreamReader(socket.getInputStream());
				while ((len = reader.read(ch)) != -1) {
					infomation = infomation + String.valueOf(ch, 0, len);
				}
				System.out.println(infomation);
				AnalyseMessage analyze = new AnalyseMessage(infomation);
				
				port = analyze.getClientPort();
				message = analyze.getMessage();
				messType = analyze.getMessType();
				userName = analyze.getUserName();
				address = socket.getInetAddress().toString().substring(1);
				sendedUser = analyze.getSendedUser();
				
				dealWithMessage(messType);
				
				reader = null;
			} catch (IOException e) {
				e.printStackTrace();
				portField.setEditable(true);
			}
		}
	}
	
	public String getClientAddress(){
		return socket.getInetAddress().toString().substring(1);
	}
	
	//对不同的消息类型进行处理
	public void dealWithMessage(int messType){
		switch(messType){
		case 0 : 
			
		case 1 :
			
		case 2 :
			
		case 3 :
				dealWithLogin(userInfo,userName,port,address,userInfoList);
		}
	}
	
	public void dealWithDefault(){
		
	}
	
	public void dealWithLogin(JList userInfo,String userName,int port,String address,Vector userInfoList){
		String name = userName;
		String userInformation = "";
		if(name.length() > 18){
			name = name.substring(0, 14);
			name = name + "...";
		}
		userInformation = userInformation + name;
		for(int i = 0;i < (24 - name.length());i++){
			userInformation = userInformation + " ";
		}
		userInformation = userInformation +port;
		
		for(int i = 0;i < (62 - userInformation.length());i++){
			userInformation = userInformation + " ";
		}
		userInformation = userInformation +address;
		userInfoList.add(userInformation);
		
		userInfo.setListData(userInfoList);
		UserInfo aUserInfo = new UserInfo();
		aUserInfo.setAddress(address);
		aUserInfo.setPort(port);
		aUserInfo.setPosition(userInfoList.size()); //设置用户信息在vetor中的某个位置，以便用户退出时进行用户信息的删除操作
		allUserMap.put(name, aUserInfo);
		System.out.println("-------------");
		System.out.println(allUserMap.size());
	}
	
	public void dealWithLogout(){
		
	}
	
	public void dealWithSendAll(){
		
	}
}
