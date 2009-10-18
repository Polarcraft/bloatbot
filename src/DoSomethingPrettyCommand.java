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
		if(message.equals("the fishbot impersonation")){
			bot.changeNick("Fishbööt");
			bot.sendMessage(channel, "m000");
			bot.sendMessage(channel, "There is no spoon");
			bot.sendMessage(channel, "... I'm stupid. Bloatbots better");
		
		}
		
	}

}
