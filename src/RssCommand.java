import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jibble.pircbot.PircBot;

import com.sun.cnpi.rss.elements.Category;
import com.sun.cnpi.rss.elements.Item;
import com.sun.cnpi.rss.elements.Rss;
import com.sun.cnpi.rss.parser.RssParser;
import com.sun.cnpi.rss.parser.RssParserException;
import com.sun.cnpi.rss.parser.RssParserFactory;


public class RssCommand implements BotCommand {
	private ArrayList<String> feeds;
	private FileWriter out;
	private FileInputStream fstream;
	private String file;

	public RssCommand() {
		file = "feeds.txt";
		feeds = new ArrayList<String>();
		feeds = getFeeds();
	}

	public String getCommand() {
		return "rss";
	}

	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		if (args[1].equals("add")) {
			addFeed(args[2]);
		} else if (args[1].equals("remove")) {
			int position = Integer.parseInt(args[2]);
			if (position < 1 || position > feeds.size()) {
				bot.sendMessage(channel, "Could not find feed.");
			} else {
				removeFeed(position);
				bot.sendMessage(channel, "Feed #" + position + " removed.");
			}
		} else if (args[1].equals("list")) {
			int counter = 1;
			String count = "";
			feeds = getFeeds();
			bot.sendMessage(channel, "Feed count: " + feeds.size()
					+ " and here is the list: ");			
			for (String feed : feeds) {
				count = String.format("%02d.", counter);
				bot.sendMessage(channel, count + " " + feed);
				counter++;
			}
		} else if (args[1].equals("count")) {			
			bot.sendMessage(channel, "I'm currently listening to "
					+ feeds.size() + " feeds.");
		} else if (args[1].equals("clear")) {
			bot.sendMessage(channel, "All feeds cleared.");
			clearFeeds();
		} else if (args[1].equals("update")) {
			int counter = 1;
			feeds = getFeeds();
			System.out.println(feeds.size());
			for(String feed : feeds) {
				Item i = parseFeed(feeds.get(counter));
				bot.sendMessage(channel, String.format("%02d.", counter) + " - " + i.getTitle() + " [" + i.getLink() +"]");
				counter++;
			}
			System.out.println("Counter: " + counter);
		}
	}

	public void addFeed(String feed) {
		try {
			out = new FileWriter(new File(file), true);
			BufferedWriter w = new BufferedWriter(out);			
			w.write(feed);
			w.newLine();
			w.close();
			out.close();
			System.out.println("Added feed: " + feed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeFeed(int position) {
		try {
			File inFile = new File(file);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			// Construct the new file that will later be renamed to the original
			// filename.
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;
			int currentPosition = 0;

			// Read from the original file and write to the new
			// unless content matches data to be removed.
			while ((line = br.readLine()) != null) {
				currentPosition++;

				if (currentPosition != position) {
					System.out.println(line);
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			// Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<String> getFeeds() {
		try {
			fstream = new FileInputStream("feeds.txt");
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

	public void clearFeeds() {
		try {
			out = new FileWriter(new File("feeds.txt"), false);
			out.close();
			System.out.println("Cleared feeds. Size: " + feeds.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Item parseFeed(String feed) {
		try {
			RssParser parser = RssParserFactory.createDefault();
			Rss rss = parser.parse(new URL(feed));
			Item feedItem = null;
			
			// Get all XML elements in the feed
			Collection items = rss.getChannel().getItems();
			if(items != null && !items.isEmpty()) {
				for(Iterator i = items.iterator(); i.hasNext(); System.out.println()) {
					feedItem = (Item)i.next();					
					System.out.println("Title: " + feedItem.getTitle());
					System.out.println("Link: " + feedItem.getLink());
					System.out.println("Description: " + feedItem.getDescription());
					return feedItem;
				}
			}
			return null;
		} catch (RssParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
