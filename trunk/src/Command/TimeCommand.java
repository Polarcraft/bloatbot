package Command;
import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;
import java.util.Date;

public class TimeCommand implements BotCommand {

	public String getCommand() {		
		return "time";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args) {
		bot.sendMessage(channel, sender + ":" + Colors.YELLOW + " The time is now " + (new Date()));
	}

}
