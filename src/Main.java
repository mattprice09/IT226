import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static Map<String, Student> allStudents = new HashMap<String, Student>();
	public static ArrayList<Class> allClasses = new ArrayList<Class>();

	// Helper method that returns a map of all row indeces mapped to data 'type'
	private static Map<Integer, String> headerHelper(String [] parts) {
		Map<Integer, String> output = new HashMap<Integer, String>();
		for (int i = 0; i < parts.length; i++) {
			
			// Find the column that contains student id's
			if (parts[i].contains("id")) {
				output.put(i, "id");
			}
		}
		
		return output;
	}
	
	public static void ReadData(String fileName) {
		File f = new File(fileName);
		Scanner mainScanner = new Scanner(f);
		
		// Get first line
		String line = mainScanner.nextLine();
		String [] parts = line.split(",");
		
		HashMap<Integer, String> headers = (HashMap<Integer, String>) headerHelper(parts);
		
		while (mainScanner.hasNextLine()) {
			line = mainScanner.nextLine();
			parts = line.split(",");
			
			for (int i = 0; i < parts.length; i++) {
				String stuID = "";
				String stuFName = "";
				String stuLName = "";
				
				// get column type
				if (headers.containsKey(i)) {
					String val = headers.get(i);
					if (val == "id") {
						stuID = parts[i];
					} else if (val == "classGrade") {
						
					}
				}
				
				Student stu = new Student();
				insertStudent("asdfaj;l", stu);
			}
		}
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
	
	private static void insertStudent(String stuID, Student stu) {
		
	}

	public static void main(String[] args) {

	}
}
