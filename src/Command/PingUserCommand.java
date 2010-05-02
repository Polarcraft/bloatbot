package Command;


import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;


public class PingUserCommand implements BotCommand{


	
	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		return "ping";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
			StringBuilder st = new StringBuilder("");
			for(User s: bot.getUsers(channel)){
				st.append(s.getNick() + "");
			}
			
			bot.sendMessage(channel, st.toString());
			
			
		
	}

}
