import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

//	public static ArrayList<Student> allStudents = new ArrayList<Student>();
	public static Map<String, Student> allStudents = new HashMap<String, Student>();
	public static ArrayList<Class> allClasses = new ArrayList<Class>();

	public static void ReadData(String fileName) {
		File f = new File(fileName);
	}

	public static void MainLoop() {
		Scanner in = new Scanner(System.in);
		String input = "";

		// Loop until user enters 'exit'
		while (!input.equals("exit")) {
			System.out.println("Choose a command:");
			System.out.println("A - Add data");
			System.out.println("S - Save data");
			System.out.println("G - Query students");
			
			input = in.next();
			
			// Add data
			if (input.equals("a") || input.equals("A")) {
				ReadData(input);
			}
			
			// Save data
			if (input.equals("s") || input.equals("S")) {
				
			}
			
			// Query data
			if (input.equals("g") || input.equals("G")) {
//				System.out.println("Enter a course name (ex: IT226). Enter 'none' to skip.");
//				String className = in.next();
//				for (String id : class1.getStudents()) {
//					Student curr = allStudents.get(id);
//				}
			}
		}
	}

	public static void main(String[] args) {

	}
}
