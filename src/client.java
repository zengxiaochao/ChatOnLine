import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


//�ͻ��˳���ͨ��login��½�ɹ�����
public class client extends JFrame implements ActionListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1658872007515743902L;
	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private JButton button;
	private String name;
	mianban m;  //������Ϣ���
	JScrollPane m2;
	write go_line = new write();    //���������ߣ�д�����ļ�

	//��������������������ʾ��ͳ��
	JPanel panel_2;
	person_list panel_2_2;
	
 
	private static int clientport;
	private static String clientip;
	static Socket socket;
	static BufferedWriter bw;
	//static��̬�������ļ���ֻ����һ��  
	static {
		Properties p = new Properties(); 
		try {
			p.load(new FileReader("chat.properties"));
			//�����Ը�ֵ
			clientport = Integer.parseInt(p.getProperty("clientPort"));
			clientip = p.getProperty("clientIp");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 

	int sizex = 510;
	private JLabel label;
	public client(String name) {
		go_line.goline(name);                      //���ߣ�д�������ļ�
		this.name = name;
		this.setBounds(100, 100, 1100, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 572, 1064, 147);
		this.getContentPane().add(panel);
		panel.setLayout(null);

		button = new JButton("\u53D1\u9001");
		button.setBounds(902, 99, 118, 38);
		panel.add(button); 

		textField = new JTextField();
		textField.setBounds(0, 0, 857, 147);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton button_1 = new JButton("\u968F\u673A\u8868\u60C5");
		button_1.addActionListener(new ActionListener() {                       //���ͼƬ
			public void actionPerformed(ActionEvent e) {
				Random r = new Random();
				int ran1 = r.nextInt(24)+1;
				String message_to = panel_2_2.message_to();     //��Ϣ������
				Date date = new Date();                                                              //ʱ���
				SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
				m.addf(" ���ң��� "+message_to+"    ("+formatter.format(date)+")");
				send_picture("src/res/"+ran1+".gif",message_to);

			}
		});
		button_1.setBounds(902, 51, 118, 38);
		panel.add(button_1);

//		JPanel panel_1 = new JPanel();
//		panel_1.setBounds(10, 10, 496, 353);
//		this.getContentPane().add(panel_1);
//		panel_1.setLayout(null);
//		//�ı���
//		textArea = new JTextArea();
//		textArea.setBounds(0, 0, 496, 353);
//
//
//		//������
//		JScrollPane scrollPane = new JScrollPane(textArea);
//		scrollPane.setBounds(0, 0, 496, 353);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		panel_1.add(scrollPane);
		
		 
		//	����һ����������Ϣ���
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 859, 512);
		this.getContentPane().add(panel_1);

		m = new mianban();
		m2=new JScrollPane(m);
		m2.getVerticalScrollBar().setUnitIncrement(20);
		panel_1.add(m2, BorderLayout.CENTER);
		m2.setPreferredSize(new Dimension(860, sizex));
		this.setFocusable(true);
		this.setVisible(true);

		//����һ���������������
		panel_2 = new JPanel();
		panel_2.setBounds(881, 10, 195, 560);
		this.getContentPane().add(panel_2); 
		panel_2_2 = new person_list();
		JScrollPane panel_2_2_g=new JScrollPane(panel_2_2); 
		panel_2.add(panel_2_2_g, BorderLayout.CENTER);
		panel_2_2_g.getVerticalScrollBar().setUnitIncrement(20);
		panel_2_2_g.setPreferredSize(new Dimension(195, 560));
		
		label = new JLabel("\u53D1\u9001\u7ED9\uFF1A");
		label.setFont(new Font("����", Font.PLAIN, 18));
		label.setBounds(799, 535, 78, 27);
		getContentPane().add(label);
		this.setFocusable(true);
		this.setVisible(true);
		panel_2_2.fresh_front();                               //ˢ����������
		
		
//		JPanel panel_2 = new JPanel();
//		panel_2.setBounds(879, 10, 195, 512);
//		this.getContentPane().add(panel_2);
//		panel_2.setLayout(null);
//
//		JPanel panel_3 = new JPanel();
//		panel_3.setBounds(10, 532, 1064, 30);
//		this.getContentPane().add(panel_3);
//		panel_3.setLayout(null);
//
		this.setTitle(this.name);
		this.setVisible(true);

		
		
		//���ڹرռ������������ļ�����״̬
		addWindowListener(new WindowAdapter()
			{
				@Override
		            public void windowClosing(WindowEvent e) {
	            // �˴������������
					go_line.offline(name);
					validate();
				}
			}
				
		);
		
		
		/***************************�ͻ���****************************/
		//		��ť����
		try {
			socket = new Socket(clientip, clientport);
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2����ȡsocket����
		button.addActionListener(this);
		textField.addKeyListener(this);


		new Thread(new Runnable() {                 //���̶߳��û�
			@Override
			public void run() {

				//1������һ���ͻ��˵��׽���
				try {

					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//3����ȡsocket���

					String line = null;
					while ((line = br.readLine()) != null) {
						Date date = new Date();                                                              //ʱ���
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
						if(line.charAt(0)==' ')                                                       //���������ַ����ո�ͷ���Ƿ������Ĺ���
						{
							m.addg("���棺    ("+formatter.format(date)+")");
							line="      "+line;
							m.addt(line);
							sizex+=30;
							m.setPreferredSize(new Dimension(700, sizex));
							setVisible(true);
						}
						else {
							String from = "",to = "";
							int name_to = 0;
							int x = 0;
							if(line.charAt(0)=='@')                                                    //���������ַ���@��ͷ���Ǻ��ѵ�ͼƬ
								x+=1;
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
							String message = line.substring(x+3);
							String name_all = "������ ˵��";
							if((name.equals(to)||to.equals(name_all))&&!from.equals(name))    //ȷʵ����Ȩ�޽��յ���Ϣ
							{
								m.addf(from+":  ("+formatter.format(date)+")");
								if(line.charAt(0)=='@')    //ͼƬ��Ϣ
								{
									m.addp(message);
									m.addt("");
									sizex+=510;
									m.setPreferredSize(new Dimension(700, sizex));
									setVisible(true);
								}
								
								if(line.charAt(0)!='@')     //������Ϣ
								{
									m.addt(message);
									sizex+=30;
									m.setPreferredSize(new Dimension(700, sizex));
									setVisible(true);
								}
							}
						}
						


					}
					socket.close();												//4���ر�ͨ��
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}




	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		send();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			send();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void send()
	{
		String message_to = panel_2_2.message_to();     //��Ϣ������
		String te = textField.getText();
		if(te.equals(""))
		{
			JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�գ�");
			return ;
		}
		Date date = new Date();                                                              //ʱ���
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss  yyyy/MM/dd");
		m.addf(" ���ң��� "+message_to+"    ("+formatter.format(date)+")");
	     //����������
		m.addt(te);
		sizex+=30;
		m.setPreferredSize(new Dimension(700, sizex));
		setVisible(true);

		te = name + "->" + message_to + "$$:"+te;
		try {
			 
			textField.setText("");
			bw.write(te);
			bw.newLine();
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}
	}
	void send_picture(String picture,String message_to)
	{
		//"src/res/01.gif"
		m.addt("");
		m.addp(picture);
		m.addt("");
		sizex+=510;
		m.setPreferredSize(new Dimension(700, sizex));
		setVisible(true);
		String te = "@"+name + "->" + message_to + "$$:"+picture;
		try {
			bw.write(te);
			bw.newLine();
			bw.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}
		
	}
}
