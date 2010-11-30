package Command;
import org.jibble.pircbot.PircBot;


public class dcHubCommand implements BotCommand{

	@Override
	public String getCommand() {
	
		return "dc";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		bot.sendMessage(channel, "ckack.privat.kejsarmakten.se:9092 Status:[unimplemented]");
		
	}

}
