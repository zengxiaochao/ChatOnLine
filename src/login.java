//客户端登陆入口
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class login extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7414249611735731998L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	public static void main(String[] args) {
		new login();
	} 

	/** 
	 * Create the application.
	 */
	public login() { 
		this.setBounds(100, 100, 666, 457); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 650, 418);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnNewButton = new JButton("登录");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(275, 308, 177, 42);
		panel.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(242, 189, 229, 42);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(242, 240, 229, 42);
		panel.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("即时通讯");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 53));
		lblNewLabel.setBounds(209, 39, 293, 106);
		panel.add(lblNewLabel);
		
		
		//按钮响应

		btnNewButton.addActionListener(this);

		
		this.setResizable(false);//窗口大小不可变
		this.setTitle("登录");
		this.setVisible(true); 
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		String id = textField.getText();
		String pwd = passwordField.getText();
		read R = new read();
		try{
		
			if(R.pwd(id).equals(pwd))
			{
				if(R.online(id)==1)
				{
					JOptionPane.showMessageDialog(null, "改账户已登入，请勿重复登录！！！！");
				}
				else {
					success();
				}
			}
			else {
				nosuccess();
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "没有该用户，请正确输入用户名！！！！");
		}
	}
	void success()
	{
		client c = new client(textField.getText());
		this.dispose();
	}
	void nosuccess()
	{
		JOptionPane.showMessageDialog(null, "请验证你的账户密码是否正确！！！！");
	}
}
