package Command;

import java.util.HashMap;
import java.util.Set;

import org.jibble.pircbot.PircBot;

/**
 * This commands contains statistics about how much
 * different users on the channel have been talking.
 * @author fredrik.strandin
 * @version 0.1 beta
 */
public class StatCommand implements BotCommand {
	private HashMap<String, Integer> stats;
	private String command;

	public StatCommand() {
		stats = new HashMap<String, Integer>();
		command = "stat";
	}

	@Override
	public String getCommand() {
		return command;
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		bot.sendMessage(channel, "Stats for the different users:");
		Set<String> nicks = stats.keySet();
		for (String nick : nicks) {
			bot.sendMessage(channel, nick + ": " + stats.get(nick));
		}
	}
	
	/**
	 * Adds 1 to the number of times <code>nick</code> has said something
	 * in the channel.
	 * @param nick the nickname for the person
	 */
	public void addStat(String nick) {
		if (stats.containsKey(nick)) {
			Integer stat = stats.get(nick);
			stat = Integer.valueOf(stat + 1);
			stats.put(nick, stat);
		} else {
			stats.put(nick, Integer.valueOf(1));
		}
	}

}
