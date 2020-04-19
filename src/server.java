import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class server extends JFrame implements ActionListener, KeyListener{
	
	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JButton button;
	ServerSocket serversocket = null;
	mianban m;  //聊天消息面板
	JScrollPane m2;
	//下面两个，在线人数显示和统计
	JPanel panel_2;
	person_list panel_2_2;

	Socket socket;
	static List<Socket> socketList = new ArrayList(); 
	int sum = 0; 


	//服务端口 
	private static int serverport;
	//static静态代码块读文件，只加载一次
	static {
		Properties p = new Properties();
		try {
			p.load(new FileReader("chat.properties"));
			//给属性赋值
			serverport = Integer.parseInt(p.getProperty("serverPort"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		server s = new server();
		s.newclient();
	}

	int sizex = 510;
	private JLabel label;
	public server() {
		this.setBounds(100, 100, 1100, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 571, 1064, 148);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		button = new JButton("\u53D1\u9001");
		button.setBounds(903, 89, 118, 38);
		panel.add(button);

		textField = new JTextField();
		textField.setBounds(0, 0, 865, 148); 
		panel.add(textField);
		textField.setColumns(10);




		//	下面一段是聊天消息面板
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 861, 514);
		this.getContentPane().add(panel_1);

		m = new mianban();
		m2=new JScrollPane(m);
		panel_1.add(m2, BorderLayout.CENTER);
		m2.getVerticalScrollBar().setUnitIncrement(20);
		m2.setPreferredSize(new Dimension(860, sizex));
		this.setFocusable(true);
		this.setVisible(true);


//
//		//文本框
//		textArea = new JTextArea();
//		textArea.setBounds(0, 0, 496, 353);
//
//		//滚动条
//		JScrollPane scrollPane = new JScrollPane(textArea);
//		scrollPane.setBounds(0, 0, 496, 353);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		panel_1.add(scrollPane);

		
		
		//下面一段是在线人数面板
		panel_2 = new JPanel();
		panel_2.setBounds(881, 10, 195, 560);
		this.getContentPane().add(panel_2); 
		panel_2_2 = new person_list();
		JScrollPane panel_2_2_g=new JScrollPane(panel_2_2); 
		panel_2.add(panel_2_2_g, BorderLayout.CENTER);
		panel_2_2_g.getVerticalScrollBar().setUnitIncrement(20);
		panel_2_2_g.setPreferredSize(new Dimension(195, 560));
		this.setFocusable(true);
		this.setVisible(true);
		panel_2_2.fresh_front();                               //刷新在线人数
		
		
		
		
		
		
		
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 534, 861, 27);
		this.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		label = new JLabel("\u53D1\u9001\u7ED9\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 17));
		label.setBounds(789, 0, 72, 27);
		panel_3.add(label);

		this.setTitle("服务器");
		this.setVisible(true);

		/***************************服务器****************************/

		button.addActionListener(this);
		textField.addKeyListener(this);

	}


	public void newclient() throws Exception {

		try {
			//1、创建一个服务端的套接字
			serversocket = new ServerSocket(serverport);
			//多线程运行
			//2、等待客户端连接
			while(true)
			{
				socket = serversocket.accept();
				panel_2_2.fresh_front();//刷新服务端在线人数，
				//2、获取socket输入
				sum++;
				System.out.println("第" + sum + "个客户已连接");

				new Thread(new Runnable() {                 //多线程多用户
					@Override
					public void run() {

						socketList.add(socket);

							BufferedReader br = null;
							try {
								br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								String line = null;

								while((line = br.readLine())!=null)
								{

									String from = "",to = "";
									int name_to = 0;
									int x = 0;
									if(line.charAt(0)=='@')
										x++;
									for (; x < line.length(); x++) {
										if(line.charAt(x)=='-'&&line.charAt(x+1)=='>')
										{
											name_to++;
											x+=2;
										}
										if(line.charAt(x)=='$'&&line.charAt(x+1)=='$'&&line.charAt(x+2)==':')
										{
											break;
										}
										if(name_to==0)
											from+=line.charAt(x);
										else
											to+=line.charAt(x);
									}
									String name_all = "全体";
									if(to.equals(name_all))
										to = "所有人 说：";
									String message = line.substring(x+3);
//									System.out.println(message);
									if(line.charAt(0)!='@')
										forward(from, to, message);                                          //服务器向外转发用户的消息
									if(line.charAt(0)=='@')
										forwardPicture(from, to, message);      
									Date date = new Date();                                                              //时间戳
									SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
									m.addf(from+" 对 "+to+"       ("+formatter.format(date)+")");
									if(line.charAt(0)=='@')                                //收到的是图片
									{
										m.addp(message);
										sizex+=510;
									}
									else {                                                    //收到的是文字
										m.addt(message);
										sizex+=30;
									}
									m.setPreferredSize(new Dimension(700, sizex));
									setVisible(true);

								}
							} catch (Exception e) {
								panel_2_2.fresh_front();//刷新服务端在线人数，
							}
					}
				}).start();
			}
			//4、关闭通道

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		send();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			send();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void send()
	{
		String message_to = panel_2_2.message_to();   //消息接收人
		String te = textField.getText();
		if(te==null)
		{
			JOptionPane.showMessageDialog(null, "发送内容不能为空！");
			return ;
		}
		
		Date date = new Date();                                                              //时间戳
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
		m.addg("公告:（"+formatter.format(date)+"）");
		te = "           ==>>@" + message_to +"       "+te;
		m.addg(te);
		sizex+=80;
		m.setPreferredSize(new Dimension(700, sizex));
		setVisible(true);

		Iterator<Socket> iterator = socketList.iterator();
		while (iterator.hasNext())
		{
				try {
					Socket temp = iterator.next();
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(temp.getOutputStream()));
					textField.setText("");
					bw.write(te);
					bw.newLine();
					bw.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					iterator.remove();
				}
		}
	}
	void forward(String message_from,String message_to,String message)
	{
		String message_end = message_from + "->" + message_to + "$$:"+message;
		Iterator<Socket> iterator = socketList.iterator();
		while (iterator.hasNext())
		{
			try {
				Socket temp = iterator.next();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(temp.getOutputStream()));
				bw.write(message_end);
				bw.newLine();
				bw.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();	
			}
			
		}
	}
	void forwardPicture(String message_from,String message_to,String message)
	{
		String message_end = "@"+message_from + "->" + message_to + "$$:"+message;
		Iterator<Socket> iterator = socketList.iterator();
		while (iterator.hasNext())
		{
			try {
				Socket temp = iterator.next();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(temp.getOutputStream()));
				bw.write(message_end);
				bw.newLine();
				bw.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();	
			}
			
		}
	}
}
