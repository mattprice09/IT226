package Asg2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.text.SimpleDateFormat;
import org.jdatepicker.*;
import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class Main {

	private static JFrame frame;

	public static ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	public static final String dateFormat = "yyyy/MM/dd";

	// Kevin/Randy
	public static void initiateAll() {

	}

	// Matt
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

					// instantiate Alarm object
					Alarm newAlarm;
					if (msg.equals("")) {
						newAlarm = new Alarm(inputDT);
					} else {
						newAlarm = new Alarm(inputDT, msg);
					}
					
					// start timer for the new alarm
					int totalSeconds = (int) LocalDateTime.from(LocalDateTime.now()).until(inputDT, ChronoUnit.SECONDS);
					newAlarm.startTimer(totalSeconds);
				
					alarms.add(newAlarm);
					
					// close the popup
					removePopup(submitBtn);
					System.out.println(alarms.size());
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

	// Helper that removes the current frame that contains the button that was
	// clicked.
	private static void removePopup(JButton button) {
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

	private static void newTimerPopup() {

	}

	// Randy
	private static void writeAlarm() {

	}

	// Randy
	private static void writeAll() {

	}

	// Kevin
	public static void removeAlarm() {

	}

	public static void main(String[] args) {
		LocalDate now = LocalDate.now();
		LocalDateTime dtnow = LocalDateTime.now();

		LocalDateTime then = dtnow.plusMinutes(30);
		if (dtnow.isAfter(then)) {
			System.out.println("is after");
		} else {
			System.out.println("is before");
		}
		System.out.println(then);

		displayUI();

		// Timer timer = new Timer();
		// Alarm alarm = new Alarm();
		// alarm.startTimer(2);

		// initiateAll();
		// displayUI();
	}
}
