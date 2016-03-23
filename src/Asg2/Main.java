package Asg2;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Main {

	public static void main(String [] args) {
		LocalDate now = LocalDate.now();
		LocalDateTime dtnow = LocalDateTime.now();
		
		LocalDateTime then = dtnow.plusMinutes(30);
		if (dtnow.isAfter(then)) {
			System.out.println("is after");
		} else {
			System.out.println("is before");
		}
		System.out.println(then);
	}
}
