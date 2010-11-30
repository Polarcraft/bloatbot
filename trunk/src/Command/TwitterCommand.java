package Command;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.jibble.pircbot.PircBot;


public class TwitterCommand implements BotCommand {
	private PircBot bot;
	private ArrayList<String> twitters;

	public TwitterCommand(PircBot bot) {
		this.bot = bot;
		twitters = getFeeds("twitters.txt");
	}

	@Override
	public String getCommand() {
		return "twitter";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		bot.sendMessage(channel, "Awesome Twitter-client!");
		bot.sendMessage(channel, "Reads:");
		for (String twitter : twitters) {
			bot.sendMessage(channel, "\u00b7" + twitter);
		}
	}

	public ArrayList<String> getFeeds(String file) {
		try {
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			ArrayList<String> lines = new ArrayList<String>();

			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
				System.out.println(line);
			}

			in.close();
			return lines;
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		return null;
	}

}
