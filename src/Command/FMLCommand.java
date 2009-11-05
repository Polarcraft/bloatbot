package Command;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jibble.pircbot.PircBot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FMLCommand implements BotCommand{

	private URL fmlURL;
	private NodeList nodeLst;

	public FMLCommand() {
		generateFMLs();
	}

	private void generateFMLs() {
		try {
			fmlURL = new URL(
					"http://api.betacie.com/view/random?key=readonly&language=en");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(fmlURL.openStream());
			doc.getDocumentElement().normalize();
			nodeLst = doc.getElementsByTagName("text");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFML() {		
			Element textElmnt = (Element) nodeLst.item(0);
			NodeList text = textElmnt.getChildNodes();
			generateFMLs();
			return ((Node) text.item(0)).getNodeValue();
	}
	
	@Override
	public String getCommand() {		
		return "fml";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender,
			String message, String[] args) {
		bot.sendMessage(channel, getFML());		
	}
}
