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
		if (start_stopServer.getText() == "��������") {
			Font font = new Font("����", Font.BOLD, 14);
			start_stopServer.setForeground(Color.RED);
			start_stopServer.setFont(font);
			start_stopServer.setText("ֹͣ����");
		} else if (start_stopServer.getText() == "ֹͣ����") {
			Font font = new Font("����", Font.PLAIN, 14);
			start_stopServer.setForeground(Color.BLACK);
			start_stopServer.setFont(font);
			start_stopServer.setText("��������");
		}
	}
}
