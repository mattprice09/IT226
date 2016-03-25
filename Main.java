package Asg2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;

public class Main {

	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	// Kevin/Randy
	public static void initiateAll() {

	}

	// Matt
	public static void displayUI() {

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

//		Timer timer = new Timer();
//		Alarm alarm = new Alarm();
//		alarm.startTimer(2);

//		initiateAll();
//		displayUI();
	}
}
