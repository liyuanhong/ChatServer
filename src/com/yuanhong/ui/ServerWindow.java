package com.yuanhong.ui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.yuanhong.listener.StartStooServerListener;

public class ServerWindow {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("\u670D\u52A1\u5668");
		frame.setBounds(100, 100, 468, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel serverPort = new JLabel("\u7AEF\u53E3\u53F7\uFF1A");
		serverPort.setBounds(10, 10, 60, 15);
		serverPort.setFont(new Font("宋体", Font.BOLD, 14));
		frame.getContentPane().add(serverPort);
		
		textField = new JTextField();
		textField.setBounds(71, 7, 66, 21);
		textField.setFont(new Font("宋体", Font.PLAIN, 14));
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton start_stopServer = new JButton("\u542F\u52A8\u670D\u52A1");
		start_stopServer.setBounds(324, 6, 112, 23);
		start_stopServer.setFont(new Font("宋体", Font.PLAIN, 14));
		frame.getContentPane().add(start_stopServer);
		start_stopServer.addMouseListener(new StartStooServerListener(start_stopServer));
		
		JLabel lblNewLabel = new JLabel("\u6240\u6709\u5728\u7EBF\u7528\u6237\uFF1A");
		lblNewLabel.setBounds(10, 38, 111, 15);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 432, 349);
		frame.getContentPane().add(scrollPane);
		
		JTextArea txtrAsdfAsdfAds = new JTextArea();
		txtrAsdfAsdfAds.setLineWrap(true);
		txtrAsdfAsdfAds.setEditable(false);
		scrollPane.setViewportView(txtrAsdfAsdfAds);
		
		JLabel lblNewLabel_1 = new JLabel("\u7528\u6237\u540D\uFF1A           \u7AEF\u53E3\u53F7\uFF1A           ip\u5730\u5740\uFF1A");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 63, 432, 15);
		frame.getContentPane().add(lblNewLabel_1);
	}
}

