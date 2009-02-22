package sessionmon;

import java.util.Enumeration;
import java.util.Random;

import javax.servlet.http.HttpSession;

import sessionmon.util.RandomStringUtils;

public class SessionTesterTool {
	public static void addStringAttributes(int numOfAttributes, HttpSession session) {
		for(int i=0; i<numOfAttributes; i++) {
			String name = "sessionAttribute_" + i;
			Random random = new Random();
			int randomLength = random.nextInt(200);
			String value = RandomStringUtils.randomAlphanumeric(randomLength);
			
			session.setAttribute(name, value);
		}
	}
	
	public static void removeAttributes(HttpSession session) {
		Enumeration e = session.getAttributeNames();
		while(e.hasMoreElements()) {
			session.removeAttribute((String)e.nextElement());
		}
	}
}
