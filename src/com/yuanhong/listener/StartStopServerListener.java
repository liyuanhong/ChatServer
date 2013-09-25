package com.yuanhong.listener;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONObject;

import com.yuanhong.service.MainService;
import com.yuanhong.util.MessageClass;
import com.yuanhong.util.MessageType;
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
	private JFrame window;
	

	public StartStopServerListener(JButton start_stopServer,
			ServiceCtrol serviceCtrol,MainService mainService,
			ServicePort port,JTextField portField,JList userInfo,Vector userInfoList,
			Map<String, UserInfo> allUserMap,JFrame window) {
		this.start_stopServer = start_stopServer;
		this.serviceCtrol = serviceCtrol;
		this.mainService = mainService;
		this.port = port;
		this.portField = portField;
		this.userInfo = userInfo;
		this.userInfoList = userInfoList;
		this.allUserMap = allUserMap;
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int serverPort;
		if(portField.getText().toString() == ""){
			JOptionPane.showMessageDialog(window, "端口号不能为空！", "警告", JOptionPane.ERROR_MESSAGE);
	}else{
		try{
			serverPort = Integer.parseInt(portField.getText().toString());
			if(1024 > serverPort || serverPort > 49151){
				JOptionPane.showMessageDialog(window, "请输入1024~49151", "警告", JOptionPane.ERROR_MESSAGE);
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
					sendAllUserServerCloseMessage();
					
					try {
						serSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(window, "请输入1024~49151的数字", "警告", JOptionPane.ERROR_MESSAGE);
		}	
	}
	}
	
	public void sendAllUserServerCloseMessage(){
		for(Iterator<String> ite = allUserMap.keySet().iterator();ite.hasNext();){
			MessageClass message;
			UserInfo userInfor_inner = null;
			try {		
				String theSendeUser = ite.next().toString();
				userInfor_inner = (UserInfo)allUserMap.get(theSendeUser);
				message = new MessageClass();		
				message.setMessType(MessageType.SERVERCLOSE);
				
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
		allUserMap.clear();
		userInfoList.clear();
		userInfo.setListData(userInfoList);
	}
}
