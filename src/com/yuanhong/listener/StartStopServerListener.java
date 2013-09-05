package com.yuanhong.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class StartStopServerListener extends MouseAdapter {
	JButton start_stopServer;
	

	public StartStopServerListener(JButton start_stopServer) {
		this.start_stopServer = start_stopServer;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (start_stopServer.getText() == "启动服务") {
			Font font = new Font("宋体", Font.BOLD, 14);
			start_stopServer.setForeground(Color.RED);
			start_stopServer.setFont(font);
			start_stopServer.setText("停止服务");
		} else if (start_stopServer.getText() == "停止服务") {
			Font font = new Font("宋体", Font.PLAIN, 14);
			start_stopServer.setForeground(Color.BLACK);
			start_stopServer.setFont(font);
			start_stopServer.setText("启动服务");
		}
	}
}
