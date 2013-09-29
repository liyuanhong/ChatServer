package com.yuanhong.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuanhong.util.AnalyseMessage;
import com.yuanhong.util.MessageClass;
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
	private String sendedUser;              //要发送消息的用户
	private JList userInfo;
	private Vector userInfoList;
	private Map<String, UserInfo> allUserMap;

	private int messType;
	private String message;
	private String userName;                  //发送消息的用户
	private int port;
	private String address;
	private String logoutUser = "";                //退出聊天的用戶

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
			dealWithDefault(userName);
			break;
		case 1 :
			dealWithSendAll();
			break;
		case 2 :
			dealWithLogout();
			break;
		case 3 :
			dealWithLogin(userInfo,userName,port,address,userInfoList);
			break;
		}
	}
	
	public void dealWithDefault(String userName){
		String name = "";
		UserInfo userInfor_inner = null;
		MessageClass message;
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){
			if(ite.hasNext()){
				if((name = ite.next()).equals(sendedUser)){
					userInfor_inner = (UserInfo)allUserMap.get(name);
					
					try {
						message = new MessageClass();		
						message.setUserName(userName);
						message.setMessType(MessageType.DEFAULT);
						message.setMessage(this.message);
						
						JSONObject json = new JSONObject(message.getJsonMap());
						
						Socket soc = new Socket(userInfor_inner.getAddress(), userInfor_inner.getPort());
						OutputStreamWriter output = new OutputStreamWriter(soc.getOutputStream());
						output.write(json.toString());
						output.close();
						soc.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//发送1表示用户已经占用
	public void dealWithLogin(JList userInfo,String userName,int port,String address,Vector userInfoList){
		if(isUserNameUsed(userName, allUserMap)){
			sendLoginStatus(port, address, "1");
		}else{
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
			allUserMap.put(userName, aUserInfo);
			
			Map<String, Object> map = allUserToMap(allUserMap);
			sendUserList(port, address, map);
		}
	}
	
	//当用户退出时,服务器执行的操作
	public void dealWithLogout(){
		userInfoList = new Vector();
		UserInfo theUser =  allUserMap.get(userName);
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){	
			if(ite.next().equals(userName)){
				allUserMap.remove(userName);
				logoutUser = userName;
				break;
			}	
		}	
		
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){
			String theName = ite.next().toString();
			UserInfo user = allUserMap.get(theName);
			String name = theName;
			String userInformation = "";
			if(name.length() > 18){
				name = name.substring(0, 14);
				name = name + "...";
			}
			userInformation = userInformation + name;
			for(int i = 0;i < (24 - name.length());i++){
				userInformation = userInformation + " ";
			}
			userInformation = userInformation +user.getPort();
			
			for(int i = 0;i < (62 - userInformation.length());i++){
				userInformation = userInformation + " ";
			}
			userInformation = userInformation +user.getAddress();
			userInfoList.add(userInformation);
		}
		userInfo.setListData(userInfoList);
		
		sendLogoutMessage(allUserMap);
	}
	
	public void dealWithSendAll(){
		sendAllUserMessage(allUserMap);
	}
	
	public boolean isUserNameUsed(String userName,Map<String, UserInfo> allUserMap){
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){
			if(ite.next().equals(userName)){
				return true;
			}
		}
		return false;
	}
	
	
	public void sendLoginStatus(int clientPort,String clientAddress,String status){
		try {
			Socket soc = new Socket(clientAddress, clientPort);
			OutputStreamWriter output = new OutputStreamWriter(soc.getOutputStream());
			output.write(status);
			output.close();
			soc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//发送用户列表给用户
	public void sendUserList(int port,String address,Map<String, Object> allUserMap){
		JSONObject json = new JSONObject(allUserMap);
		MessageClass message = new MessageClass();
		message.setMessType(3);
		message.setMessage(json.toString());
		JSONObject jsonSend = new JSONObject(message.getJsonMap());
		for(Iterator ite = allUserMap.keySet().iterator();ite.hasNext();){
			try {			
				JSONObject user = new JSONObject((allUserMap.get(ite.next().toString())).toString());
				sendLoginStatus(user.getInt("port"), user.getString("address"),jsonSend.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	//将所有的用户转换为map
	public Map<String, Object> allUserToMap(Map<String, UserInfo> allUserMap){
		Map<String, Object> map = new TreeMap<String, Object>(); 
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){
			Map<String, Object> submap = new TreeMap<String, Object>();
			String name = ite.next().toString();
			UserInfo userInfo = allUserMap.get(name);
			submap.put("address", userInfo.getAddress());
			submap.put("port", userInfo.getPort());
			map.put(name, submap);
		}
		return map;
	}
	
	//当某用户退出后向所有用户发送用户列表
	public void sendLogoutMessage(Map<String, UserInfo> allUserMap2){
		JSONObject json = new JSONObject(allUserMap2);
		MessageClass message = new MessageClass();
		message.setMessType(2);
		message.setMessage(logoutUser);
		JSONObject jsonSend = new JSONObject(message.getJsonMap());
		for(Iterator ite = allUserMap2.keySet().iterator();ite.hasNext();){
			UserInfo user = (UserInfo) allUserMap2.get(ite.next());
			sendLoginStatus(user.getPort(), user.getAddress(),jsonSend.toString());	
		}
	}
	
	public void sendAllUserMessage(Map<String, UserInfo> allUserMap){
		for(Iterator ite = allUserMap.keySet().iterator();ite.hasNext();){
			MessageClass message;
			UserInfo userInfor_inner = null;
			try {						
				String theSendeUser = ite.next().toString();
				userInfor_inner = (UserInfo)allUserMap.get(theSendeUser);
				if(userName.equals(theSendeUser)){
					
				}else{
					message = new MessageClass();		
					message.setSendedUser(userName);
					message.setMessType(MessageType.DEFAULT);
					message.setMessage(this.message);
					
					JSONObject json = new JSONObject(message.getJsonMap());
					
					Socket soc = new Socket(userInfor_inner.getAddress(), userInfor_inner.getPort());
					OutputStreamWriter output = new OutputStreamWriter(soc.getOutputStream());
					output.write(json.toString());
					output.close();
					soc.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}








