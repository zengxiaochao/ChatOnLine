import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;


public class mianban extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	public mianban() {
		setBackground(Color.white);
		setForeground(Color.white);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	public void addf(String print)
	{

		JTextArea a = new JTextArea(1,70);
		if(print.charAt(0)==' ')
		{
			a.setForeground(new Color(0, 128, 0));
			a.setText(print); 
		}
		else
			a.setText("  "+print);
		a.setTabSize(55);
		a.setFont(new Font("宋体", Font.BOLD, 12));
		a.setEditable(false);
		this.add(a);
	}
	public void addg(String print)
	{
		JTextArea a = new JTextArea(1,50);
		if(print.charAt(0)=='=')
			a.setForeground(new Color(255, 0, 0));
		else
			print = "                                                 "+print;
		a.setText(print);
		a.setTabSize(55);
		a.setFont(new Font("宋体", Font.BOLD, 15));
		a.setEditable(false);
		this.add(a);
	}
	public void addt(String print)
	{
		int spl; 
		for(spl = 0;spl<print.length();spl+=50)
		{
			int string_end = spl+50;
			if(spl+50>=print.length())
			{
				string_end = print.length();
			}
			String string_temp = print.substring(spl,string_end);
			JTextArea a = new JTextArea(1,75);
			a.setText(string_temp);
			a.setTabSize(55);
			a.setEditable(false);
			this.add(a);
		}
	}
	public void addp(String picture)
	{
		Icon icon=new ImageIcon(picture);
		JLabel label = new JLabel(icon, JLabel.CENTER);
		addt("");
		this.add(label);
	}
}
