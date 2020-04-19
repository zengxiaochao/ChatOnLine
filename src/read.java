import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class read {
	int online(String name)
	{
		Properties p = new Properties();
		try {
			p.load(new FileReader("online.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int on= Integer.parseInt(p.getProperty(name));
		p.clone();
		return on;
	}
	String pwd(String name)
	{
		
		Properties p = new Properties();
		String pass;
		try {
			p.load(new FileReader("pwd.properties"));
			pass = p.getProperty(name);
		} catch (IOException e) {
			return "NO_person_NO";
		}
		p.clone();
		return pass;
	}
}
