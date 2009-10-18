import org.jibble.pircbot.PircBot;


public class GodNattCommand implements BotCommand{

	@Override
	public String getCommand() {
	
		return "godnatt";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		bot.sendMessage(channel, "Already? Wussies! Me and my fellow cohorts will stay here all night sipping Martini and plot schemes.");
		
	}

}
