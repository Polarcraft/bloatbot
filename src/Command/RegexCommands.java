package Command;

import org.jibble.pircbot.PircBot;

public abstract class RegexCommands implements BotCommand{

	
	public abstract String getCommand();

	public abstract void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args);

	public abstract boolean regexSearch(String message);
}
