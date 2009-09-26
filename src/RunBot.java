import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.jibble.*;

public class RunBot {
	public static void main(String[] args) {
		Properties config = new Properties();
		
		try {
			config.load(new FileInputStream("pbdemo.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading config file: pbdemo.properties");
			e.printStackTrace();
			System.exit(0);
		}		
		
		BloatBot b = new BloatBot(config);
	}
}
