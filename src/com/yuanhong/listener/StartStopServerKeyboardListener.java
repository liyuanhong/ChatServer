package com.yuanhong.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

public class StartStopServerKeyboardListener extends KeyAdapter{
	JButton start_stopServer;
	
	public StartStopServerKeyboardListener(JButton start_stopServer){
		this.start_stopServer = start_stopServer;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
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
}
