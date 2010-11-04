import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jibble.pircbot.*;

import socnet.Configuration;
import socnet.Graph;
import socnet.Node;
import socnet.SocialNetworkBot;

import Command.AddLinkCommand;
import Command.BotCommand;
import Command.ChangeNickCommand;
import Command.CourseCommand;
import Command.DivideByZeroCommand;
import Command.DoSomethingPrettyCommand;
import Command.FMLCommand;
import Command.GodNattCommand;
import Command.HelloCommand;
import Command.PingUserCommand;
import Command.QuitCommand;
import Command.ReciteCommand;
import Command.RegexCommands;
import Command.RssCommand;
import Command.StatCommand;
import Command.TimeCommand;
import Command.dcHubCommand;
import Command.irritateFishbotCommand;




/**
 * 
 * 
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
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 * @modified Zolomon @ 2009
 */
public class BloatBot extends PircBot {

	// Store the commands
	private final List<BotCommand> commands;
	
	// Not prefix triggered commands
	private final List<BotCommand> otherCommands;
	private final StatCommand statCommand;
	
	// Regex triggered commands
	private final List<RegexCommands> regexCommands;
	
	//private String channel;
	private Properties config;
	
	// There's a bug in the getArgument() method for when count < 0 and quotation is true, it doesn't 
	// consider the quote as one argument if there are spaces between the quotes.
	private int maxArgs = 3;

	// The character which tells the bot we're talking to it and not anything
	// else.
	private final String prefix = "bloatbot ";

	public BloatBot() throws IOException {
		config = getProperties("pbdemo.properties");
		//channel = config.getProperty("channel");
		
		statCommand = new StatCommand();
		AddLinkCommand addLink = new AddLinkCommand(config);
		
		commands = new ArrayList<BotCommand>();

		// Add all commands
		commands.add(new TimeCommand());
		commands.add(new QuitCommand());
		commands.add(new HelloCommand());
		commands.add(new RssCommand());
		commands.add(new FMLCommand());
		commands.add(new CourseCommand());	
		commands.add(new ReciteCommand());
		commands.add(new DivideByZeroCommand());
		commands.add(new ChangeNickCommand());
		commands.add(new DoSomethingPrettyCommand());
		commands.add(new GodNattCommand());
		commands.add(new dcHubCommand());
		commands.add(new PingUserCommand());
		commands.add(statCommand);
		commands.add(addLink);
		commands.add(new BotCommand(){
			public String getCommand(){
				return "commands";
			}
			// The method where each BotCommand implementor will handle the event
			public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args){
				String sum = "";
				for(BotCommand c : commands){
					sum += c.getCommand() +", ";
				}
				sendMessage(channel, sum);
			}
		});
		
		
		// DANGEROUS. DOES NOT SCALE!!!!!!!
		// Commands that does not need keyword bloatbot first
		otherCommands = new ArrayList<BotCommand>();
		otherCommands.add(new irritateFishbotCommand());
		regexCommands = new ArrayList<RegexCommands>();
		//Regex commands
		regexCommands.add(addLink);
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
		
		/*
		 * Social bot
		 * 
		 */
		
		  	Properties p = new Properties();
	        String configFile = "./config.ini";
	        try {
				p.load(new FileInputStream(configFile));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        socialConfig = new Configuration(p);
	        
	        setVerbose(socialConfig.verbose);        
	        try {
	            setEncoding(socialConfig.encoding);
	        }
	        catch (UnsupportedEncodingException e) {
	            // Stick with the platform default.
	        }
	        
	       
	        
		 // Prevent construction if the output directory does not exist.
        if (!socialConfig.outputDirectory.exists() || !socialConfig.outputDirectory.isDirectory()) {
          
				throw new IOException("Output directory (" + socialConfig.outputDirectory + ") does not exist.");
			
        }
	}

	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		
		// DANGEROUS. DOES NOT SCALE!!!!!!! 
		// Checks for commands not triggered by Nick.
		for(BotCommand commands : otherCommands){
			if (message.startsWith(commands.getCommand())){
				commands.handleMessage(this, channel, sender, 
					message.replace(commands.getCommand(), "").trim(), null);
			}
		}
		// more dangerous shit. nothing ever scales -_-
		// Checks for regex triggered Commands
		for(RegexCommands command : regexCommands){
			String[] args = getArguments(message, maxArgs);
			command.regexSearch(this, channel, sender, 
						message.trim(), args);
			
		}
		
		// Find out if message was for this bot
		if (message.startsWith(prefix)) {
			message = message.replace(prefix, "");
			
			String[] args = getArguments(message, maxArgs);
			
			// Find out the command given (in a very simplistic way) ... this
			// doesn't scale
			for (BotCommand command : commands) {
				// if the message starts with the commands the BotCommand
				// respStringTokenizeronds to, remove
				// the command from the message and pass the event along to the
				// BotCommand
				if (message.startsWith(command.getCommand())) {
					command.handleMessage(this, channel, sender, 
							message.replace(command.getCommand(), "").trim(), args);
				}
			}
		}
		
		statCommand.addStat(sender);
		
		/* socialbot */
		   add(channel, sender);
	        
	        // Pass the message on to the InferenceHeuristics in the channel's Graph.
	        String key = channel.toLowerCase();
	        Graph graph = (Graph) _graphs.get(key);
	        graph.infer(sender, Colors.removeFormattingAndColors(message));
	}

	public void onConnect() {
		String authBot = config.getProperty("authbot");
		String authMessage = config.getProperty("authmessage");
		String hostmask = config.getProperty("hostmask");

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

	public Properties getProperties(String filepath) {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream(filepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error loading config file: pbdemo.properties");
			e.printStackTrace();
			System.exit(0);
		}

		return config;
	}
	
	public String[] getArguments(String input, int count) {
		return getArguments(input, count, true);
	}

	/**
	 * 
	 * Här hamnar alla login events. Vet ej vad super.onJoin gör så jag kallar den för säkerhets skull.
	 * Gör den inget så gör det ju ignet att vi kallar den heller ^^.
	 * 
	 */
	@Override
	public void onJoin(String channel, String sender, String login, String hostname){
		super.onJoin(channel, sender, login, hostname);
		if(sender.equals("respekt") || sender.equals("rspkt"))
			sendMessage(channel, "Respekt "+ sender +"!");
		if(sender.equals("kd35a"))
			sendMessage(channel, "Welcome " +sender);
		if(sender.equals("Zolomon"))
			sendMessage(channel , "Everybody greet Zoiman!");
		if(sender.equals("bloatbot2"))
			sendMessage(channel, "NOOOOOes, there is two of me! Bloatbots sad ._. ");
		if(sender.equals("Ralleballe"))
			sendMessage(channel, "Welcome "+ sender);
		if(sender.equals("bloatbot"))
			sendMessage(channel, "Hello, I arrive!");
		if(sender.equals("Bobbanovich") || hostname.equals("c-83-233-144-112.cust.bredband2.com")){
			sendMessage(channel, "http://kd35a.se/skit/robin.jpg wahaha");
			sendAction(channel, "ollar robin!");
		if(sender.equals("Forsyth") || sender.equals("Forsyth-"))
			sendMessage(channel, "Wow, hi " + sender + ", this place sure got a lot nicer now!");
			
		}
//		if(hostname.equals("c-83-233-152-86.cust.bredband2.com") && !sender.equals("Ralleballe"))
//			sendMessage(channel, "Kolla " + sender + " = Ralleballe! :D (http://sv.wikipedia.org/wiki/Balle_(runristare)) ");

		/* socialbot*/

        add(channel, sender);
        
        if (sender.equalsIgnoreCase(getNick())) {
            // Remember that we're meant to be in this channel
            _channelSet.add(channel.toLowerCase());
        }
	}
	
	protected void onPrivateMessage(String sender, String login, String hostname, String message){
		super.onPrivateMessage(sender, login, hostname, message);
		sendMessage("#d10", sender + " makes me nervous.");
	}
	public String[] getArguments(String input, int count, boolean quotations) {
		// TODO Fix the quotation bug.
		if(count < 0) {			
			return input.split(" ");
		} else {
			String[] args = new String[count];
			int offset = 0;
			int total = 0;
			int next = 0;

			for(int i = 0; i < count && next != -1; i++) {
				next = input.indexOf(" ", offset);

				if(next == -1) {
					args[i] = input.substring(offset);
				} else {
					args[i] = input.substring(offset, next);

					if(quotations && args[i].startsWith("\"")) {
						int quote = input.indexOf('"', offset + 1);
						
						if(quote == -1) {
							args[i] = input.substring(offset + 1);
							next = -1;
						} else {
							args[i] = input.substring(offset + 1, quote);
							if(quote + 1 < input.length() && input.charAt(quote + 1) == ' ') {
								next = quote + 1;
							} else if(quote + 1 == input.length()) {
								next = -1;
							} else {
								next = quote;
							}
						}
					}
				}
				offset = next + 1;
				total++;
			}

			return (total == count)? args : Arrays.copyOfRange(args, 0, total);
		}
	}
	/*------------------------------------------------------------- socialbot */
	
	 // HashMap of String -> Graph objects.
    private HashMap _graphs = new HashMap();

    // Used to remember which channels we should be in
    private HashSet _channelSet = new HashSet();

    private Configuration socialConfig;
	
	 protected void onAction(String sender, String login, String hostname, String target, String action) {
	        if ("#&!+".indexOf(target.charAt(0)) >= 0) {
	            onMessage(target, sender, login, hostname, action);
	        }
	    }
	    // Overridden from PircBot.
	    protected void onUserList(String channel, User[] users) {
	        for (int i = 0; i < users.length; i++) {
	            add(channel, users[i].getNick());
	        }
	    }
	    // Overridden from PircBot.
	    protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason) {
	        add(channel, kickerNick);
	        add(channel, recipientNick);
	        
	        if (recipientNick.equalsIgnoreCase(getNick())) {
	            // The bot was kicked, so rejoin the channel (if possible).
	            joinChannel(channel);
	        }
	    }
	    
	    // Overridden from PircBot.
	    protected void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
	        add(channel, sourceNick);
	    }
	    
	    // Overridden from PircBot.
	    protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
	        changeNick(oldNick, newNick);
	    }
	    
	
	    
	    private void add(String channel, String nick) {

	        if (socialConfig.ignoreSet.contains(nick.toLowerCase())) {
	            return;
	        }

	        Node node = new Node(nick);
	        String key = channel.toLowerCase();
	        
	        // Create the Graph for this channel if it doesn't already exist.
	        Graph graph = (Graph) _graphs.get(key);
	        if (graph == null) {
	            if (socialConfig.createRestorePoints) {
	                graph = readGraph(key);
	            }
	            if (graph == null) {
	                graph = new Graph(channel, socialConfig);
	            }
	            _graphs.put(key, graph);
	        }
	        
	        // Add the Node to the Graph.
	        graph.addNode(node);
	    }

	    private void changeNick(String oldNick, String newNick) {
	        // Effect the nick change by calling the mergeNode method on all Graphs.
	        Iterator graphIt = _graphs.values().iterator();
	        while (graphIt.hasNext()) {
	            Graph graph = (Graph) graphIt.next();
	            Node oldNode = new Node(oldNick);
	            Node newNode = new Node(newNick);
	            graph.mergeNode(oldNode, newNode);
	        }
	    }
	    
	    // Read a serialized graph from disk.
	    private Graph readGraph(String channel) {
	        Graph g = null;
	        // Try and see if the graph can be restored from file.
	        try {
	            String strippedChannel = channel.toLowerCase().substring(1);
	            
	            File dir = new File(socialConfig.outputDirectory, strippedChannel);
	            File file = new File(dir, strippedChannel + "-restore.dat");
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
	            String version = (String) ois.readObject();
	            if (version.equals(SocialNetworkBot.VERSION)) {
	                // Only read the object if the file is for the correct version.
	                g = (Graph) ois.readObject();
	            }
	            ois.close();
	        }
	        catch (Exception e) {
	            // Do nothing?
	        }
	        return g;
	    }
	    
	    public Configuration getConfig() {
	        return socialConfig;
	    }
	    
	    public Graph getGraph(String channel) {
	        channel = channel.toLowerCase();
	        return (Graph) _graphs.get(channel);
	    }
}
