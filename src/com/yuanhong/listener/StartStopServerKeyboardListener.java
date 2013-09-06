package com.yuanhong.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import com.yuanhong.service.MainService;
import com.yuanhong.util.ServiceCtrol;
import com.yuanhong.util.ServicePort;

public class StartStopServerKeyboardListener extends KeyAdapter{
	private JButton start_stopServer;
	private ServiceCtrol serviceCtrol;
	private MainService mainService;
	private ServicePort port;
	
	
	
	public StartStopServerKeyboardListener(JButton start_stopServer,ServiceCtrol serviceCtrol,MainService mainService,ServicePort port){
		this.start_stopServer = start_stopServer;
		this.serviceCtrol = serviceCtrol;
		this.mainService = mainService;
		this.port = port;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if (start_stopServer.getText() == "启动服务") {
				Font font = new Font("宋体", Font.BOLD, 14);
				start_stopServer.setForeground(Color.RED);
				start_stopServer.setFont(font);
				start_stopServer.setText("停止服务");
				serviceCtrol.setCtrol(0);
				mainService = new MainService(port,serviceCtrol);
				mainService.start();	
			} else if (start_stopServer.getText() == "停止服务") {
				Font font = new Font("宋体", Font.PLAIN, 14);
				start_stopServer.setForeground(Color.BLACK);
				start_stopServer.setFont(font);
				start_stopServer.setText("启动服务");
				serviceCtrol.setCtrol(1);
			}
		}
	}
}
