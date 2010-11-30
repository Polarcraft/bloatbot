package Command;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class PingUserCommand implements BotCommand{
	
	@Override
	public String getCommand() {
		return "ping";
	}
	
	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		StringBuilder st = new StringBuilder("");
		String nick;
		for(User s: bot.getUsers(channel)){
			nick = s.getNick();
			if (!nick.toLowerCase().equals("zolomon") &&
			    !nick.toLowerCase().equals("zol")) {
				st.append(s.getNick() + " ");
			}
		}
		bot.sendMessage(channel, st.toString());
	}
	
}
