import org.jibble.pircbot.PircBot;


public class irritateFishbotCommand implements BotCommand{

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "There is no spoon.";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		
		
			bot.sendMessage(channel, "spoon");
			
		
		
	}

}
