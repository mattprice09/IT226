import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static Map<String, Student> allStudents = new HashMap<String, Student>();
	public static ArrayList<Class> allClasses = new ArrayList<Class>();

	// Helper method that returns a map of all row indeces mapped to data 'type'
	private static Map<Integer, String> headerHelper(String[] parts) {
		Map<Integer, String> output = new HashMap<Integer, String>();
		for (int i = 0; i < parts.length; i++) {

			// Find the column that contains student id's
			if (parts[i].contains("id")) {
				output.put(i, "id");
			}
		}

		return output;
	}

	public static void ReadData(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner mainScanner = new Scanner(f);

		// Get first line
		String line = mainScanner.nextLine();
		String[] parts = line.split(",");

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

				// Student stu = new Student();
				// insertStudent("asdfaj;l", stu);
			}
		}
	}

	/**
	 * Handle query from user after the user entered 'g' or 'G'.
	 */
	public static void HandleQuery(String className, String semester, String year) {
		// User entered "none" for only one of semester or year.
		if (semester.equals("none") ^ year.equals("none")) {
			System.out.println("Invalid input. Each semester season must have a corresponding year, and vice-versa.");
			return;
		}

		if (!(className.equals("none") && semester.equals("none") && year.equals("none"))) {
			// User entered input other than "none"
			String classID = "";
			if (!(className.equals("none") || semester.equals("none") || year.equals("none"))) {
				// User entered all 3 fields
				classID = className + "-" + semester + "-" + year;
			} else if (!className.equals("none")) {
				// User only entered class name
				classID = className;
			} else {
				// User only entered semester and year
				classID = semester + "-" + year;
			}

			// Run the query and print out the scores
			int[] scores = queryScores(classID);

			// No search results from user input
			if (scores == null) {
				System.out.println("Bad input. No classes with the given information were found.");
				return;
			}

			// Good input. Print results
			Character[] letters = { 'A', 'B', 'C', 'D', 'F' };
			for (int i = 0; i < scores.length; i++) {
				System.out.println("# of " + letters[i] + "'s: " + scores[0]);
			}
		}
	}

	private static void insertStudent(String stuID, Student stu) {
		allStudents.put(stuID, stu);
	}

	// Helper to aggregate scores data
	private static int[] queryScores(String classID) {
		int[] scores = new int[6];
		boolean foundOne = false;

		// Check every class for an ID match
		for (Class clss : allClasses) {
			if (clss.getClassID().contains(classID)) {
				foundOne = true;
				// Get scores for all students in the matched class
				for (String id : clss.getStudents()) {
					Character grade = allStudents.get(id).getClassGrade(clss.getClassID());
					scores[getNumeric(grade)]++;
				}
			}
		}

		// User's input resulted in nothing being found.
		if (!foundOne) {
			return null;
		}

		return scores;
	}

	// Get the appropriate integer value for the input letter grade
	private static int getNumeric(Character letter) {
		if (letter.equals('A')) {
			return 0;
		}
		if (letter.equals('B')) {
			return 1;
		}
		if (letter.equals('C')) {
			return 2;
		}
		if (letter.equals('D')) {
			return 3;
		}

		// student got an F
		return 4;
	}

	public static void MainLoop() throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		String input = "";

		// Loop until user enters 'exit'
		while (!input.equals("e")) {
			System.out.println("Choose a command:");
			System.out.println("A - Add data");
			System.out.println("S - Save data");
			System.out.println("G - Query students");
			System.out.println("E - Exit");

			input = in.next().toLowerCase().trim();
			
			// Exit
			if (input.equals("e")) {
				System.exit(0);
			}

			// Add data
			if (input.equals("a")) {
				ReadData(input);
			}

			// Save data
			if (input.equals("s")) {

			}

			// Query data
			if (input.equals("g")) {
				System.out.println("Enter a course name (ex: IT226). Enter 'none' to skip.");
				String className = in.next().toLowerCase().trim();

				System.out.println("Enter the semester season (ex: fall, summer, spring). Enter 'none' to skip.");
				String semester = in.next().toLowerCase().trim();

				System.out.println("Enter the calendar year of the semester. Enter 'none' to skip.");
				String year = in.next().toLowerCase().trim();

				HandleQuery(className, semester, year);
			}
		}
		in.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		MainLoop();
	}
}
