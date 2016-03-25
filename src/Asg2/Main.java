package Asg2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class Main {
	
	private static JFrame frame;

	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	// Kevin/Randy
	public static void initiateAll() {

	}

	// Matt
	public static void displayUI() {
		
		// main frame
		frame = new JFrame();
		frame.setBounds(100, 100, 597, 433);
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

	// Kevin
	public static void addAlarm(LocalDateTime endDT) {

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

//		Timer timer = new Timer();
//		Alarm alarm = new Alarm();
//		alarm.startTimer(2);

//		initiateAll();
//		displayUI();
	}
}
