package com.yuanhong.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextField;

import com.yuanhong.util.AnalyseMessage;
import com.yuanhong.util.MessageType;
import com.yuanhong.util.ServiceCtrol;

public class MainService extends Thread {
	private String infomation = "";
	private ServerSocket serSocket;
	private Socket socket;
	private InputStreamReader reader;
	private ServiceCtrol serviceCtrol;
	private JTextField portField;
	private String sendedUser;

	private int messType;
	private String message;
	private String userName;
	private int port;
	private String addrress;

	public MainService(ServiceCtrol serviceCtrol, ServerSocket serSocket,
			JTextField portField) {
		this.serviceCtrol = serviceCtrol;
		this.serSocket = serSocket;
		this.portField = portField;
	}

	@Override
	public void run() {
		int len;
		char[] ch = new char[512];
		while (serviceCtrol.getCtrol() == 0) {
			infomation = "";
			try {
				socket = serSocket.accept();
				System.out.println(socket.getInetAddress().toString().substring(1));
				System.out.println("========================");
				reader = new InputStreamReader(socket.getInputStream());
				while ((len = reader.read(ch)) != -1) {
					infomation = infomation + String.valueOf(ch, 0, len);
				}
				System.out.println(infomation);
				AnalyseMessage analyze = new AnalyseMessage(infomation);
				
				port = analyze.getPort();
				message = analyze.getMessage();
				messType = analyze.getMessType();
				userName = analyze.getUserName();
				addrress = analyze.getAddress();
				sendedUser = analyze.getSendedUser();
				
				
				System.out.println(port + " , " + message + " , " + messType + " , " + userName + " , " + addrress);
				
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

		}
	}
	
	public void dealWithDefault(){
		
	}
	
	public void dealWithLogin(){
		
	}
	
	public void dealWithLogout(){
		
	}
	
	public void dealWithSendAll(){
		
	}
}
