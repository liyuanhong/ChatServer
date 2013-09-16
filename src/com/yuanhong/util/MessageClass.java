package com.yuanhong.util;

import java.util.HashMap;
import java.util.Map;

public class MessageClass {
	private int messType;
	private Destination destination;
	private String message;
	private String userName;
	public int getMessType() {
		return messType;
	}
	public void setMessType(int messType) {
		this.messType = messType;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//��json����ת��Ϊ�ַ���
	public String getJsonString(){
		return getJsonMap().toString();
	}
	
	//�������е��ֶ�ת��Ϊjson����
	public Map<String, Object> getJsonMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> subMap = new HashMap<String, Object>();
		
		subMap.put("address", destination.getAddress());
		subMap.put("port", destination.getPort());		
		
		map.put("messType", this.getMessType());
		map.put("destination", subMap);
		map.put("message", this.getMessage());
		map.put("userName", this.getUserName());
		
		return map;
	}
}
