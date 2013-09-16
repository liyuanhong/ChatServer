package com.yuanhong.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import com.yuanhong.service.MainService;
import com.yuanhong.util.ServiceCtrol;
import com.yuanhong.util.ServicePort;
import com.yuanhong.util.UserInfo;

public class StartStopServerListener extends MouseAdapter {
	private JButton start_stopServer;
	private ServiceCtrol serviceCtrol;
	private MainService mainService;
	private ServicePort port;
	private JTextField portField;
	private ServerSocket serSocket;
	private JList userInfo;
	private Vector userInfoList;
	private Map<String, UserInfo> allUserMap;
	

	public StartStopServerListener(JButton start_stopServer,
			ServiceCtrol serviceCtrol,MainService mainService,
			ServicePort port,JTextField portField,JList userInfo,Vector userInfoList,
			Map<String, UserInfo> allUserMap) {
		this.start_stopServer = start_stopServer;
		this.serviceCtrol = serviceCtrol;
		this.mainService = mainService;
		this.port = port;
		this.portField = portField;
		this.userInfo = userInfo;
		this.userInfoList = userInfoList;
		this.allUserMap = allUserMap;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(portField.getText().toString() == ""){
		System.out.println("port is null");
	}else{
		if (start_stopServer.getText() == "启动服务") {
			Font font = new Font("宋体", Font.BOLD, 14);
			start_stopServer.setForeground(Color.RED);
			start_stopServer.setFont(font);
			start_stopServer.setText("停止服务");
			portField.setEditable(false);
			serviceCtrol.setCtrol(0);
			port.setPort(Integer.parseInt(portField.getText()));
			
			try {
				this.serSocket = new ServerSocket(port.getPort());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			mainService = new MainService(serviceCtrol,serSocket,portField,userInfo,userInfoList,allUserMap);
			mainService.start();	
		} else if (start_stopServer.getText() == "停止服务") {
			Font font = new Font("宋体", Font.PLAIN, 14);
			start_stopServer.setForeground(Color.BLACK);
			start_stopServer.setFont(font);
			start_stopServer.setText("启动服务");
			portField.setEditable(false);
			serviceCtrol.setCtrol(1);
			
			try {
				serSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	}
}
