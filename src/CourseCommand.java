
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.jibble.pircbot.PircBot;


public class CourseCommand implements BotCommand {
	private String file;

	private Scanner scanner;

	public CourseCommand(){
		file="courseinfo.txt";
	}
	
	@Override
	public String getCommand() {
		return "courselist";
	}
	

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
				
		if((sender.equals("Ralleballe89") || sender.equals("Ralleballe") ) && (args[1] == null || !args[1].equals("snälla")))
			bot.sendMessage(channel, "AHAHHAHAHAHAHAH. lol");
		else{
				try {
					scanner = new Scanner(new File(file));
				} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
				}
			
				while(scanner.hasNextLine()){
					bot.sendMessage(channel, scanner.nextLine());
				}
				
		}	
				
		
	}
	


}
