import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class write {
	public static void goline(String name)  //����
	{
		writeData(name,"1");
	}
	public static void offline(String name)//����
	{
		writeData(name,"0");
	}
	public static void writeData(String key, String value) 
	{  
	        Properties prop = new Properties();  
	        InputStream fis = null;  
	        OutputStream fos = null;  
	        try {  
	            fis = new FileInputStream("online.properties");  
	            prop.load(fis);  
	            fis.close();//һ��Ҫ���޸�ֵ֮ǰ�ر�fis 
	            fos = new FileOutputStream("online.properties");  
	            prop.setProperty(key, value);  
	            prop.store(fos, "Update '" + key + "' value");  
	            fos.close();  
	              
	        } catch (Exception e) {

	        } 
	}   
}
