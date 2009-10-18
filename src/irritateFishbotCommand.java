import org.jibble.pircbot.PircBot;


public class irritateFishbotCommand implements BotCommand{

	private static int saftySwitch =0;
	
	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "There is no spoon.";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		
			if(saftySwitch < 30){
				saftySwitch ++;
				
				bot.sendMessage(channel, "spoon");
					
			}
			
		
		
	}

}
