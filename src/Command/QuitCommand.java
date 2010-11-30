package Command;
import org.jibble.pircbot.*;

public class QuitCommand implements BotCommand {

	public String getCommand() {
		return "quit";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args) {
		bot.quitServer("now bloatbot leaves. not beacuse you tell bloatbot, but because bloatbot wants to!");
	}
	

}
