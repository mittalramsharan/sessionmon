package sessionmon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sessionmon.PageRequestProcessor;

public class File {
	public static InputStream readFileAsInputStream(String cName) {
		InputStream is = null;
		try { 
		      is = PageRequestProcessor.class.getResourceAsStream(cName);
		} catch (Exception e) {
		      e.printStackTrace();
		}
		return is;
	}
	
	public static String readFileAsString(String cName) {
		InputStream is = null;
		BufferedReader br = null;
		String line;
		StringBuffer content = new StringBuffer();

		try { 
			is = PageRequestProcessor.class.getResourceAsStream(cName);
			br = new BufferedReader(new InputStreamReader(is));
			while (null != (line = br.readLine())) {
				content.append(line);
				content.append("\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null) br.close();
				if (is != null) is.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content.toString();
	}
}
