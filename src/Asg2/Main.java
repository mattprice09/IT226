package Asg2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;
import org.jdatepicker.impl.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Main {

	private static JFrame frame;

	public static ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	public static final String dateFormat = "yyyy/MM/dd";

	// Kevin/Randy
	public static void initiateAll() {
		// read from file and fill list
		for (Alarm alarm : alarms) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime alarmStopTime = alarm.getStopTime();
			// will display alarms that went off while program was off, or reset
			// their timers
			if (alarmStopTime.isBefore(now)) {
				alarm.triggerAlarm();
			} else {
				// begin timer for alarm
				int time = (int) (now.until(alarmStopTime, ChronoUnit.SECONDS));
				alarm.setTime(time);
				alarm.startTimer();
			}
		}
	}

	/**
	 * Displays the main Swing interface to create alarms and timers
	 */
	public static void displayUI() {

		// main frame
		frame = new JFrame();
		frame.setBounds(100, 100, 716, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));

		JPanel TitlePanel = new JPanel();
		frame.getContentPane().add(TitlePanel);
		TitlePanel.setLayout(new BorderLayout(0, 0));

		// title label
		JLabel lblNewLabel = new JLabel("Alarm Manager");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitlePanel.add(lblNewLabel, BorderLayout.CENTER);

		JPanel ButtonsPanel = new JPanel();
		frame.getContentPane().add(ButtonsPanel);
		ButtonsPanel.setLayout(new BorderLayout(0, 0));

		JPanel flowPanel = new JPanel();
		ButtonsPanel.add(flowPanel, BorderLayout.CENTER);
		flowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// "create alarm" button
		JButton createAlarmBtn = new JButton("Create Alarm");
		createAlarmBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				newAlarmPopup();
			}
		});
		flowPanel.add(createAlarmBtn);
		createAlarmBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));

		// "create timer" button
		JButton createTimerBtn = new JButton("Create Timer");
		createTimerBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		flowPanel.add(createTimerBtn);
		createTimerBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JPanel bufferPanel = new JPanel();
		ButtonsPanel.add(bufferPanel, BorderLayout.NORTH);

		frame.setVisible(true);
	}

	/**
	 * Displays a "Create Alarm" Swing popup that accepts user input and creates
	 * an Alarm
	 */
	private static void newAlarmPopup() {

		JFrame newAlarmFrame = new JFrame();
		newAlarmFrame = new JFrame();
		newAlarmFrame.setBounds(100, 100, 597, 433);
		newAlarmFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newAlarmFrame.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));

		JPanel TitlePanel = new JPanel();
		newAlarmFrame.getContentPane().add(TitlePanel);
		TitlePanel.setLayout(new BorderLayout(0, 0));

		JLabel TitleLbl = new JLabel("New Alarm");
		TitleLbl.setFont(new Font("SansSerif", Font.BOLD, 40));
		TitleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		TitlePanel.add(TitleLbl, BorderLayout.CENTER);

		JPanel ButtonsPanel = new JPanel();
		newAlarmFrame.getContentPane().add(ButtonsPanel);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		ButtonsPanel.setLayout(new GridLayout(4, 1, 0, 0));
		JPanel DatePanel = new JPanel();
		ButtonsPanel.add(DatePanel);

		// "Date:" JLabel
		JLabel dateLbl = new JLabel("Date:");
		dateLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
		DatePanel.add(dateLbl);

		// Datepicker
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		DatePanel.add(datePicker);

		JPanel TimePanel = new JPanel();
		ButtonsPanel.add(TimePanel);

		// "Time:" JLabel
		JLabel timeLbl = new JLabel("Time:");
		timeLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
		TimePanel.add(timeLbl);

		// Timepicker
		JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date()); // will only show the current time
		TimePanel.add(timeSpinner);

		JPanel MsgPanel = new JPanel();
		ButtonsPanel.add(MsgPanel);

		// "Message" JLabel
		JLabel msgLbl = new JLabel("Message (Optional): ");
		msgLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
		MsgPanel.add(msgLbl);

		// Input text field
		JTextField textField = new JTextField();
		textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
		MsgPanel.add(textField);
		textField.setColumns(30);

		JPanel SubmitPanel = new JPanel();
		ButtonsPanel.add(SubmitPanel);
		SubmitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Button that creates the new alarm
		JButton submitBtn = new JButton("Submit");
		submitBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		submitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// CREATE new alarm
				if (model.getValue() != null) {
					LocalDateTime inputDT = convertInput(model.getValue(), "" + timeSpinner.getValue());
					String msg = textField.getText();

					// Don't do anything if the input datetime is
					// chronologically in the past.
					if (LocalDateTime.now().isAfter(inputDT)) {
						return;
					}

					// instantiate Alarm object
					Alarm newAlarm;
					if (msg.equals("")) {
						newAlarm = new Alarm(inputDT);
					} else {
						newAlarm = new Alarm(inputDT, msg);
					}

					// start timer for the new alarm
					newAlarm.startTimer();

					alarms.add(newAlarm);

					// close the popup
					removePopup(submitBtn);
					System.out.println("Created alarm.");
				}
			}
		});
		SubmitPanel.add(submitBtn);

		// Button that closes the current frame
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				removePopup(cancelBtn);
			}
		});
		SubmitPanel.add(cancelBtn);

		newAlarmFrame.setVisible(true);
	}

	// Under development
	private static void toggleFlash() {

	}

	// Matt
	private static void newTimerPopup() {

	}

	// Helper that removes the current frame that contains the button that was
	// clicked.
	public static void removePopup(JButton button) {
		Container currFrame = button.getParent();
		do
			currFrame = currFrame.getParent();
		while (!(currFrame instanceof JFrame));
		((JFrame) currFrame).dispose();
	}

	// Converts the input date and time into a properly formatted LocalDateTime
	// object
	private static LocalDateTime convertInput(Date date, String time) {
		String dateSelection = formattedDate(date).replace("/", "-");
		String timeSelection = "" + time;
		timeSelection = timeSelection.substring(11, 19);
		String dt = dateSelection + "T" + timeSelection + ".000";
		System.out.println(dt);
		LocalDateTime datetime = LocalDateTime.parse(dt);
		return datetime;
	}

	// Get the formatted date from a string
	private static String formattedDate(Date input) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		String inputDate = formatter.format(input);
		return inputDate;
	}

	/**
	 * Writes to files
	 */
	private static void writeAll() {

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root: alarm
			Document doc = docBuilder.newDocument();
			Element allAlarms = doc.createElement("alarms");
			doc.appendChild(allAlarms);

			for (Alarm alarm : alarms) {
				
				// Don't write deleted alarms
				if (alarm != null) {
					Element alarmElm = doc.createElement("alarm");
					allAlarms.appendChild(alarmElm);

					// Element dateTime
					Element dateTime = doc.createElement("dateTime");
					dateTime.appendChild(doc.createTextNode(alarm.getStopTime() + ""));
					alarmElm.appendChild(dateTime);

					// Element message
					Element message = doc.createElement("message");
					message.appendChild(doc.createTextNode(alarm.getMessage()));
					alarmElm.appendChild(message);

					// Element snooze
					Element snooze = doc.createElement("numSnoozes");
					snooze.appendChild(doc.createTextNode(alarm.getNumSnoozes() + ""));
					alarmElm.appendChild(snooze);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/Asg2/AlarmXML.xml"));

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

	// Kevin
	public static void removeAlarm() {
		LocalDateTime now = LocalDateTime.now();
		for (Alarm alarm : alarms) {
			if (alarm.getStopTime().isBefore(now)) {
				// null alarms should not be written to the xml instance.
				// They will be deleted because they wont be read back in.
				alarm = null;
			}
		}
	}

	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				writeAll();
			}
		}));

		initiateAll();
		displayUI();
	}
}