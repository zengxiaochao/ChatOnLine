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
	int high = 522;            //���߶�
	JLabel lblNewLabel;        //������� 
	private static Choice choice;
	public person_list() {
		setLayout(null);
		
		lblNewLabel = new JLabel(" ���ߣ�"+sum+"��");
		lblNewLabel.setFont(new Font("����", Font.BOLD, 29));
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

		

		JButton btnNewButton = new JButton("ˢ����������");
		btnNewButton.setFont(new Font("����", Font.BOLD, 14));
		btnNewButton.setBounds(0, 465, 195, 47);
		add(btnNewButton);
		add(panel_2_2_g);
		btnNewButton.addActionListener(this);
		/**********

		 //��Ϣ�����������б�
		 ************/
		choice = new Choice();
		choice.setFont(new Font("����", Font.BOLD, 15));
		choice.setBounds(0, 518, 195, 41);
		add(choice);
		choice.add("ȫ��");

		this.setVisible(true);
	}
	void addperson(String name)
	{
		JLabel tub = new JLabel("��");
		tub.setForeground(new Color(0, 255, 0));
		tub.setPreferredSize(new Dimension(30, 40));
		tub.setHorizontalAlignment(SwingConstants.LEFT);
		tub.setFont(new Font("����", Font.BOLD, 25));
		panel.add(tub);
		JLabel label = new JLabel(name);
		label.setPreferredSize(new Dimension(140, 40));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("����", Font.BOLD, 25));
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
		lblNewLabel.setText(" ���ߣ�"+sum+"��");
		high=300+50*sum;
		panel.setPreferredSize(new Dimension(195, high));
	}
	void fresh()
	{
		
		//���������ϵ����,�������б����ѡ
		choice.removeAll();
		choice.add("ȫ��");
		panel.removeAll();
		validate();
		//�����ļ��鿴ȫ����Ա
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
