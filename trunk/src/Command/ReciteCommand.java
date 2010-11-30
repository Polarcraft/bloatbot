package Command;
import org.jibble.pircbot.PircBot;

public class ReciteCommand implements BotCommand {

	@Override
	public String getCommand() {
		return "recite";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender, String message, String[] args) {
	
		if(args[1].equals("the-raven")){
			
			bot.sendMessage(channel, "Once upon a midnight dreary, while I pondered weak and weary,");
			bot.sendMessage(channel, "Over many a quaint and curious volume of forgotten lore,");
			bot.sendMessage(channel, "While I nodded, nearly napping, suddenly there came a tapping,");
			bot.sendMessage(channel," As of some one gently rapping, rapping at my chamber door.");
			bot.sendMessage(channel, "`'Tis some visitor,' I muttered, `tapping at my chamber door -");
			bot.sendMessage(channel, "Only this, and nothing more.'");
		}else{
			bot.sendMessage(channel,"Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn");
		}
	}
}
