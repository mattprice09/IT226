package Asg2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;

public class Main {

	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	// read file, add alarms to arraylist
	public static void initiateAll() {

	}

	public static void displayUI() {

	}

	private static void triggerAlarm() {

	}

	public static void addAlarm() {

	}

	private static void writeAlarm() {

	}

	private static void writeAll() {

	}

	public static void removeAlarm() {

	}

	public static void main(String[] args) {
		// LocalDate now = LocalDate.now();
		// LocalDateTime dtnow = LocalDateTime.now();
		//
		// LocalDateTime then = dtnow.plusMinutes(30);
		// if (dtnow.isAfter(then)) {
		// System.out.println("is after");
		// } else {
		// System.out.println("is before");
		// }
		// System.out.println(then);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				triggerAlarm();
			}
		}, 500);

		initiateAll();
		displayUI();
	}
}
