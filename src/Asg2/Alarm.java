package Asg2;

import java.time.LocalDateTime;
import java.time.temporal.*;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm {
	private String message;
	private LocalDateTime stopTime;
	private int numSnoozes;
	private int time;

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
				triggerAlarm();
			}
		}, inSec);
	}

	// Matt
	public static void triggerAlarm() {

	}

	public void snooze() {
		this.time = 60;
		this.startTimer();
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