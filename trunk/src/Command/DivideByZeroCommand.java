package Command;
import org.jibble.pircbot.PircBot;


public class DivideByZeroCommand implements BotCommand{

	@Override
	public String getCommand() {
		
		return "divide";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		if(message.equals("by zero")){
			bot.changeNick("EXCEPTION");
			bot.changeNick("SpppaceMonkeys");
			bot.changeNick("bloatbot");
			
		}
		if(message.equals("by infinity")){
			bot.sendMessage(channel, "Something really really tiny. Like a tiny rock or pebbles ");
			
		}
		if(message.equals("two elephants")){
			bot.sendMessage(channel, "NEVER!");
		}		
	}
}
