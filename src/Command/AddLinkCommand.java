package Command;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jibble.pircbot.PircBot;


public class AddLinkCommand extends RegexCommands{

	private final String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private Pattern pattern;
	Properties config;
	
	public AddLinkCommand(Properties config){
		this.config = config;
		pattern = Pattern.compile(regex);
	}
	
	@Override
	public String getCommand() {
		
		return "link";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		if( args.length == 0 ){
			bot.sendMessage(channel ,config.getProperty("link-base-url"));
		}else
			bot.sendMessage(channel ,config.getProperty("link-base-url") + config.getProperty("link-getlinks") + args[0]);
	}

	public void regexSearch(PircBot bot, String channel, String sender,
			String message, String[] args) {
		
		 Matcher matcher = 
	            pattern.matcher(message);
		 while(matcher.find()){
			String url = matcher.group();
			HttpURLConnection connection = null; 
			// Gets url from configfile. 
			String path = config.getProperty("linkurl") + "&url="+ url + "&author="+sender; 
			
			try{ 
				URI uri = new URI(path); 
				URL urlconnect = new URL(uri.toURL().toString()); 
				connection = (HttpURLConnection) urlconnect.openConnection(); 
				connection.connect();
				// Doesn't seem to work without the buffer. Since no responce is listened to I'll leave loop blank
				
				 BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());
		            
		            //StringBuilder builder = new StringBuilder();
		         int byteRead;
		         while ((byteRead = buffer.read()) != -1)
		              //  builder.append((char) byteRead);
		         
		         buffer.close();
		         //--------
				
			}catch(Exception e){
				System.err.println("Failed to connect to webserver");
			}
		 }

	}

}
