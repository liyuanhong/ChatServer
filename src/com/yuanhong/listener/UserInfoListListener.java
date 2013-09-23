package com.yuanhong.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import com.yuanhong.util.UserInfo;

public class UserInfoListListener extends MouseAdapter{
	private Map<String, UserInfo> allUserMap;
	private JList userInfo;
	private JFrame window;
	
	public UserInfoListListener(Map<String, UserInfo> allUserMap,JList userInfo,JFrame window) {
		this.allUserMap = allUserMap;
		this.userInfo = userInfo;
		this.window = window;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if(e.getClickCount() == 2){
			int position = userInfo.getSelectedIndex();
			Iterator ite = allUserMap.keySet().iterator();
			String name = "";
			String address = "";
			int port = 0;
			
			for(int i = 0;i <= position;i++){
				name = ite.next().toString();
			}
			
			address = allUserMap.get(name).getAddress();
			port = allUserMap.get(name).getPort();
			
			JOptionPane.showMessageDialog(window,"用户名：" + name + "\n地址：" + address + "\n端口号：" + port, "用户信息", 
					JOptionPane.DEFAULT_OPTION);
		}
	}
}
