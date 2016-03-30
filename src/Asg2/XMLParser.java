package Asg2;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	public static void parse() {
		try {
			SAXParserFactory sax = SAXParserFactory.newInstance();
			SAXParser parser = sax.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {

				boolean dateTime, message, numSnoozes = false;
				String date = "";
				String msg = "";
				String snoozes = "";

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					
					//Checks for Alarm element tags
					if (qName.equalsIgnoreCase("dateTime"))
						dateTime = true;
					if (qName.equalsIgnoreCase("message"))
						message = true;
					if (qName.equalsIgnoreCase("numSnoozes"))
						numSnoozes = true;
				}

				//Unneeded method that prints the end tags
//				public void endElement(String uri, String localName, String qName) throws SAXException {
//					System.out.println("End tag: " + qName);
//				}

				public void characters(char[] ch, int start, int length) throws SAXException {
					if (dateTime) {
						dateTime = false;
						date = new String(ch, start, length);
					}
					if (message) {
						message = false;
						msg = new String(ch, start, length);
						if (msg.equals("0"))	//Alarms w/o messages have whitespace with a value "0". 
							msg = " ";
					}
					if (numSnoozes) {
						numSnoozes = false;
						snoozes = new String(ch, start, length);
					}
					
					//Prevents empty strings from being added to Alarm objects
					if (!date.equals("") && !msg.equals("") && !snoozes.equals("")) {
						System.out.println("Date: " + date);
						System.out.println("Message: " + msg);
						System.out.println("Snoozes: " + snoozes);
						
						LocalDateTime dt = LocalDateTime.parse(date);
						Alarm newAlarm = new Alarm(dt, msg);
						int snoozeNum = Integer.parseInt(snoozes);
						newAlarm.setNumSnoozes(snoozeNum);
						date = "";
						msg = "";
						snoozes = "";
						Main.alarms.add(newAlarm);
					}
				}
			};
			parser.parse(new File("src/Asg2/AlarmXML.xml"), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}