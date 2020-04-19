import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Choice;

public class person_list extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	int sum = 0;
	JPanel panel = new JPanel();
	JScrollPane panel_2_2_g;		
	int high = 522;            //面板高度
	JLabel lblNewLabel;        //计数面板 
	private static Choice choice;
	public person_list() {
		setLayout(null);
		
		lblNewLabel = new JLabel(" 在线："+sum+"人");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 29));
		lblNewLabel.setBounds(0, 0, 195, 39);
		add(lblNewLabel);
		
		panel.setBackground(new Color(135, 206, 250));
		panel.setForeground(Color.blue);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setBounds(0, 0, 195, 464);
		panel_2_2_g=new JScrollPane(panel); 
		panel_2_2_g.getVerticalScrollBar().setUnitIncrement(20);
		panel_2_2_g.setLocation(0, 39);
		panel_2_2_g.setSize(195, 427);
		panel.setPreferredSize(new Dimension(195, high));

		

		JButton btnNewButton = new JButton("刷新在线人数");
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 14));
		btnNewButton.setBounds(0, 465, 195, 47);
		add(btnNewButton);
		add(panel_2_2_g);
		btnNewButton.addActionListener(this);
		/**********

		 //消息发送人下拉列表
		 ************/
		choice = new Choice();
		choice.setFont(new Font("宋体", Font.BOLD, 15));
		choice.setBounds(0, 518, 195, 41);
		add(choice);
		choice.add("全体");

		this.setVisible(true);
	}
	void addperson(String name)
	{
		JLabel tub = new JLabel("●");
		tub.setForeground(new Color(0, 255, 0));
		tub.setPreferredSize(new Dimension(30, 40));
		tub.setHorizontalAlignment(SwingConstants.LEFT);
		tub.setFont(new Font("宋体", Font.BOLD, 25));
		panel.add(tub);
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(140, 40));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("宋体", Font.BOLD, 25));
		panel.add(label);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		fresh_front();
	}
	void fresh_front()
	{
		sum = 0;
		fresh();
		cout();
		validate();
	}
	void cout()
	{
		lblNewLabel.setText(" 在线："+sum+"人");
		high=300+50*sum;
		panel.setPreferredSize(new Dimension(195, high));
	}
	void fresh()
	{
		
		//先清除面板上的组件,和下拉列表的人选
		choice.removeAll();
		choice.add("全体");
		panel.removeAll();
		validate();
		//遍历文件查看全部人员
		Properties prop = new Properties();
		Map<String,String> propMap=new HashMap<String, String>();
		try {
			prop.load(new FileReader("online.properties"));
			Set<Object> keyset = prop.keySet();
			for (Object object : keyset) {
				 String propValue= prop.getProperty(object.toString()).toString();
				 propMap.put(object.toString(), prop.getProperty(object.toString()).toString());
				 System.out.println(object.toString()+" : "+propValue);
				 if(propValue.equals("1"))
				 {
					 sum+=1;
					 choice.add(object.toString());
					 addperson(object.toString());
				 }
			}
			
		} catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	static String message_to()
	{
		return choice.getSelectedItem();
	}
}
