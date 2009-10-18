import org.jibble.pircbot.PircBot;


public class DoSomethingPrettyCommand implements BotCommand {

	@Override
	public String getCommand() {
		
		return "do";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		if(message.equals("something pretty in ASCII plx?")){
			bot.sendMessage(channel, "Don't know nuthin'.___. sad bloatbot. Blame Respekt.");
		
		
		
		}
		
	}

}
