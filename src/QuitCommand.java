import org.jibble.pircbot.*;

public class QuitCommand implements BotCommand {

	public String getCommand() {
		return "quit";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message) {
		bot.quitServer("Bye bye!");
	}
	

}
