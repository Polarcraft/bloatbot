package Command;

import java.util.Properties;

import org.jibble.pircbot.PircBot;

public class SocialGraphLink implements BotCommand {
	Properties config;
	public SocialGraphLink(Properties config){
		this.config = config;
	}
	@Override
	public String getCommand() {
		return "graph";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
			bot.sendMessage(channel, "The graph is located: " + config.getProperty("graphurl"));
	}

}
