package Asg2;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm {
	private String message;
	private LocalDateTime stopTime;
	private int numSnoozes;
	
	public void startTimer(int time) {
		Timer timer = new Timer();
		int inSec = time * 1000;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				triggerAlarm();
			}
		}, inSec);
	}
	
	// Matt
	public static void triggerAlarm() {
		
	}
	
	// Kevin
	public static void snooze() {
		
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
