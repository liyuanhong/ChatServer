package com.yuanhong.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.yuanhong.util.ServiceCtrol;

public class MainService extends Thread{
	private String message = "";
	private ServerSocket serSocket;
	private Socket socket;
	private int port;
	private InputStreamReader reader;
	private ServiceCtrol serviceCtrol;

	public MainService(int port,ServiceCtrol serviceCtrol) {
		this.port = port;
		try {
			this.serSocket = new ServerSocket(port);
			this.serviceCtrol = serviceCtrol;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int len;
		char[] ch = new char[512];
		System.out.println(serviceCtrol);
		while (serviceCtrol.getCtrol() == 0) {
			message = "";
			System.out.println("okokokokokokokokokokoko");
//			try {
//				socket = serSocket.accept();
//				reader = new InputStreamReader(socket.getInputStream());
//				while ((len = reader.read(ch)) != -1) {
//					message = message + String.valueOf(ch, 0, len);
//				}
//				if(message != ""){
//					System.out.println(message);
//				}	
//				reader = null;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
}
