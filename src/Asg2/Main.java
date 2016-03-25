package Asg2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;

public class Main {

	public ArrayList<Alarm> alarms = new ArrayList<Alarm>();

	// Kevin/Randy
	public static void initiateAll(ArrayList<Alarm> alarms) {
		//read from file and fill list
		for(Alarm alarm : alarms)
		{
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime alarmStopTime = alarm.getStopTime();
			//will display alarms that went off while program was off, or reset their timers
			if(alarmStopTime.isBefore(now))
			{
				alarm.triggerAlarm();
			}
			else
			{
				int time = (int) (now.until(alarmStopTime, ChronoUnit.SECONDS));
				alarm.setTime(time);
				alarm.startTimer();
			}
		}
	}

	// Matt
	public static void displayUI() {

	}

	// Kevin
	public static void addAlarm(ArrayList<Alarm> alarms, Alarm alarm) {
		alarms.add(alarm);
		alarm.startTimer();
	}

	// Randy
	private static void writeAlarm() {

	}

	// Randy
	private static void writeAll() {

	}

	// Kevin
	public static void removeAlarm(ArrayList<Alarm> alarms) {
		LocalDateTime now = LocalDateTime.now();
		for(Alarm alarm : alarms)
		{
			if(alarm.getStopTime().isBefore(now))
			{
				//null alarms should not be written to the xml instance. 
				//They will be deleted because they wont be read back in.
				alarm = null;
			}
		}
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

//		Timer timer = new Timer();
//		Alarm alarm = new Alarm();
//		alarm.startTimer(2);

//		initiateAll();
//		displayUI();
	}
}