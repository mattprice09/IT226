package Asg2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class Alarm {
	private String message;
	private LocalDateTime stopTime;
	private int numSnoozes;
	private int time;
	private boolean clicked;

	public Alarm(LocalDateTime dt) {
		this.message = "";
		this.stopTime = dt;
		this.time = (int) (LocalDateTime.now().until(this.stopTime, ChronoUnit.SECONDS));
		this.numSnoozes = 0;
	}

	public Alarm(LocalDateTime dt, String msg) {
		this.message = msg;
		this.stopTime = dt;
		this.time = (int) (LocalDateTime.now().until(this.stopTime, ChronoUnit.SECONDS));
		this.numSnoozes = 0;
	}

	public void startTimer() {
		Timer timer = new Timer();
		int inSec = this.time * 1000;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				clicked = false;
				loopAudio();
				triggerAlarm();
			}
		}, inSec);
	}

	public void triggerAlarm() {
		JFrame frame;
		frame = new JFrame();
		frame.setBounds(100, 100, 399, 311);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel alarmLabel = new JLabel("Alarm");
		alarmLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
		alarmLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(alarmLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		String time = LocalDateTime.now() + "";
		time = time.substring(11, 19);

		JLabel lblTime = new JLabel(time);
		panel.add(lblTime, BorderLayout.NORTH);
		lblTime.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);

		JTextArea textArea = new JTextArea(message);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
		textArea.setBackground(UIManager.getColor("Button.light"));
		textArea.setRows(7);
		textArea.setEditable(false);
		panel.add(textArea, BorderLayout.CENTER);

		JPanel botPanel = new JPanel();
		panel.add(botPanel, BorderLayout.SOUTH);
		botPanel.setLayout(new BorderLayout(0, 0));

		JPanel btnsPanel = new JPanel();
		botPanel.add(btnsPanel, BorderLayout.SOUTH);

		JButton SnoozeBtn = new JButton("Snooze");
		btnsPanel.add(SnoozeBtn);
		SnoozeBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JLabel numSnoozesLbl = new JLabel("You have snoozed " + this.numSnoozes + " times.");
		numSnoozesLbl.setFont(new Font("SansSerif", Font.PLAIN, 16));
		numSnoozesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		botPanel.add(numSnoozesLbl, BorderLayout.NORTH);

		// Snooze
		SnoozeBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clicked = true;
				snooze();
				Main.removePopup(SnoozeBtn);
			}
		});

		JButton DismissBtn = new JButton("Dismiss");
		DismissBtn.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnsPanel.add(DismissBtn);

		// Remove the alarm
		DismissBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clicked = true;
				Main.removeAlarm();
				Main.removePopup(DismissBtn);
			}
		});

		frame.setVisible(true);
	}

	private void loopAudio() {
		Main.playAudio();
		if (!clicked) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					loopAudio();
				}
			}, 2000);
		}
	}
	
	public void snooze() {
		this.time = 60;
		this.startTimer();
		this.stopTime = LocalDateTime.now().plusSeconds(60);
		numSnoozes++;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getTime() {
		return this.time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getStopTime() {
		return stopTime;
	}

	public void setStopTime(LocalDateTime stopTime) {
		this.stopTime = stopTime;
	}

	public int getNumSnoozes() {
		return numSnoozes;
	}

	public void setNumSnoozes(int numSnoozes) {
		this.numSnoozes = numSnoozes;
	}
}