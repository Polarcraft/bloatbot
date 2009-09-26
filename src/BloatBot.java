import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jibble.pircbot.*;

/**
 * A simple PircBot bot to show a quick and easy way to add multiple commands to
 * a bot. This allows the main bot class to stay clutter free and each command
 * has a private space to store information.
 * 
 * Each command is created and listed inside of the bot constructor. To see how
 * a command is structured, see the included BotCommand file. To see some simple
 * sample commands, check out QuitCommand and TimeCommand.
 * 
 * In order for the bot to know it is being talked to, a prefix is used. Any
 * text listed after the prefix is used to determine the command name.
 * 
 * NOTE: this requires pircbot.jar from www.jibble.org
 * 
 * @author Zolomon (http://www.Zolomon.eu) @ 2009
 */
public class BloatBot extends PircBot {

	// Store the commands
	private final List<BotCommand> commands;

	private String channel;
	private Properties authConfig;
	private Properties config;

	// The character which tells the bot we're talking to it and not anything
	// else.
	private final String prefix = ".";

	public BloatBot(Properties config) {
		authConfig = new Properties();
		config = new Properties();

		channel = config.getProperty("channel");

		commands = new ArrayList<BotCommand>();

		// Add all commands
		commands.add(new TimeCommand());
		commands.add(new QuitCommand());
		commands.add(new HelloCommand());

		// Connect to IRC
		setAutoNickChange(true);
		setName(config.getProperty("nick"));
		try {
			connect(config.getProperty("server"));
			joinChannel(config.getProperty("channel"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		// Find out if message was for this bot
		if (message.startsWith(prefix)) {
			message = message.replace(prefix, "");

			// Find out the command given (in a very simplistic way) ... this
			// doesn't scale
			for (BotCommand command : commands) {
				// if the message starts with the commands the BotCommand
				// responds to, remove
				// the command from the message and pass the event along to the
				// BotCommand
				if (message.startsWith(command.getCommand())) {
					command.handleMessage(this, channel, sender, message
							.replace(command.getCommand(), "").trim());
				}
			}
		}
	}

	public void onConnect() {
		String authBot = authConfig.getProperty("authbot");
		String authMessage = authConfig.getProperty("authmessage");
		String hostmask = authConfig.getProperty("hostmask");

		// auth
		sendRawLine("PRIVMSG " + authBot + " :" + authMessage);
		// mask the host (if set)
		if (hostmask != null && hostmask.trim().length() > 0) {
			sendRawLine(hostmask);
		}
	}

	public void onDisconnect() {
		System.exit(0);
	}

	public Properties getProperties() {
		Properties config = new Properties();

		try {
			config.load(new FileInputStream("pbdemo.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading config file: pbdemo.properties");
			e.printStackTrace();
			System.exit(0);
		}

		return config;
	}
}
