import org.jibble.pircbot.PircBot;


public class ChangeNickCommand implements BotCommand{

	@Override
	public String getCommand() {
	
		return "change";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		if(args[0].equals("nick") && args[1].equals("to")){
			bot.sendMessage(channel, "Unnecessary that will just confuce everyone!");

			bot.sendMessage(channel, "But as you wish Mr. douchy douch " + sender + " douche douche");
			System.out.println(args[2]);
			bot.changeNick(args[2]);
		}
		if(args[1].equals("nick") && args[2].equals("to")){
			bot.sendMessage(channel, "Unnecessary that will just confuce everyone!");

			bot.sendMessage(channel, "But as you wish Mr. douchy douch " + sender + " douche douche");
			System.out.println(args[3]);
			bot.changeNick(args[3]);
			
		}
		
	}

}
