package vn.edu.vnua.dse.stcalendar.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@Service
public final class AppUtils {

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	
	public static boolean isNotNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return true;
		return false;
	}
	// Properties
	public static Properties MyProperties(String file) throws IOException {
		Resource resource = new ClassPathResource(file);
		InputStream resourceInputStream = resource.getInputStream();

			Properties prop = new Properties();
			// load a properties file
			prop.load(resourceInputStream);
			return prop;
	}
	
	
	public static Path readFile(String filename) {
        ClassLoader classLoader = AppUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        String content = "";
//        try {
//            content = new String(Files.readAllBytes(Paths.get(file.getPath())));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return Paths.get(file.getPath());
    }
	public static String convertByteToHex(byte[] data) {
		  BigInteger number = new BigInteger(1, data);
		  String hashtext = number.toString(16);
		  // Now we need to zero pad it if you actually want the full 32 chars.
		  while (hashtext.length() < 32) {
		    hashtext = "0" + hashtext;
		  }
		  return hashtext;
		}
	
    public static String getMD5(String input) {
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          byte[] messageDigest = md.digest(input.getBytes());
          return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
      }
    
    public static String getScheduleMessage(String message) {
    	if(message.equals("error")) {
    		return "Có lỗi xảy ra!";
    	}
    	
    	if(message.equals("update")) {
    		return "Website đào tạo đang update dữ liệu!";
    	}
    	
    	if(message.equals("success")){
    		return "Thêm lịch thành công";
    	}
    	
    	return null;
    }
	
    public static boolean isAlertPresent(WebDriver driver)
    {
        try
        {
            driver.switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex)
        {
            return false;
        }   // catch
    }   // isAlertPresent() 
    
    public static String getString(String id) {
    	File file;
    	String string = "";
		try {
			file = ResourceUtils.getFile("classpath:xml/strings.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder buider = factory.newDocumentBuilder();
			Document doc = buider.parse(file);
			
			Element strings = doc.getDocumentElement();
			NodeList list = strings.getElementsByTagName("string");
	
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					String idElement = e.getAttribute("id");
					if(id.equals(idElement)) {
						string = e.getTextContent();
					}
				}
			}
			
		} catch (Exception e) {
			string = "";
		}
		
    	return string;
    }
	
}
