import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Main {

	public static Map<String, Student> allStudents = new HashMap<String, Student>();
	public static ArrayList<Class> allClasses = new ArrayList<Class>();

	// Helper method that returns a map of all row indeces mapped to data 'type'
	private static Map<Integer, String> headerHelper(String[] parts) {
		Map<Integer, String> output = new HashMap<Integer, String>();

		int k = 0;
		for (int i = 0; i < parts.length; i++) {
			String curr = parts[i].toLowerCase();
			// Find the column that contains student id's
			if (Pattern.compile(" id$").matcher(curr).find()) {
				// column is the ID column
				output.put(k, "id");
			} else if (curr.contains("name")) {
				// name column
				if ((!curr.contains("first")) && (!curr.contains("last"))) {
					// contains full name
					output.put(i, "lName");
					output.put(i + 1, "fName");
					k++;
				} else if (curr.contains("first")) {
					// contains first name
					output.put(k, "fName");
				} else {
					// contains last name
					output.put(k, "lName");
				}
			} else if (curr.contains("comment")) {
				// skip comments
				k++;
				continue;
			} else if (curr.contains("grade")) {
				// grade column
				output.put(k, "grade");
			} else {
				// assignment column
				output.put(k, parts[i]);
			}
			k++;
		}

		return output;
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

	// Helper to aggregate scores data
	private static int[] queryScores(String classID) {
		int[] scores = new int[5];
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

	// Helper that adds '.csv' to file name if user didn't do it
	private static String cleanFileName(String fName) {
		String output = fName;

		int index = output.indexOf(".csv");

		if (index != output.length() - 4) {
			output = output + ".csv";
		}
		return output;
	}

	// Remove the 'comments' from the input string
	private static String removeComments(String line) {
		
		// Handle quotation marks in the name fields
		String [] parts = line.split(",");
		for (int i = 0; i < 3; i++) {
			if (parts[i].contains("\"")) {
				parts[i] = parts[i].replace("\"", "").trim();
				parts[i+1] = parts[i+1].replace("\"", "").trim();
			}
		}
		line = String.join(",", parts);
		String temp = line;

		int startIndex = line.indexOf(",\"");
		// No comments in line
		if (startIndex == -1) {
			return line;
		}

		String output = line.substring(0, startIndex + 1);

		// Loop until the end of the string, removing all quoted comments
		while (startIndex != -1) {
			temp = temp.substring(startIndex + 2);
			int endIndex = temp.indexOf("\",");

			// Check if there are commas outside of quotes
			String between = temp.substring(0, endIndex);
			int numBetween = between.length() - between.replace("\"", "").length();

			if (numBetween % 2 == 0) {
				// Found end of comment
				startIndex = temp.indexOf(",\"");
				if (startIndex == -1) {
					// done with the line
					output = output + temp.substring(endIndex + 1);
				} else {
					output = output + temp.substring(endIndex + 1, startIndex + 1);
				}
			} else {
				// False instance of the sequence ",
				startIndex = -2;
				temp = temp.replaceFirst("\",", "\"");
			}
		}
		return output;
	}

	/**
	 * Read file containing class/student data and store into local data structures.
	 */
	public static void ReadData(String fileName, String classID) {
		
		File f = new File(fileName);
		Scanner mainScanner;
		try {
			mainScanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file name.");
			return;
		}

		Class clss = new Class(classID);

		// Get first line
		String line = mainScanner.nextLine();
		String[] parts = line.split(",");

		HashMap<Integer, String> headers = (HashMap<Integer, String>) headerHelper(parts);

		// Iterate through all rows in .csv file
		while (mainScanner.hasNextLine()) {
			line = mainScanner.nextLine();
			line = removeComments(line);
			parts = line.split(",");

			String stuID = "";
			String stuFName = "";
			String stuLName = "";
			// first 3 rows will always contain id, first name, and last name
			for (int i = 0; i < 3; i++) {
				// get column type
				String val = headers.get(i);
				if (val == "id") {
					stuID = parts[i];
				} else if (val == "lName") {
					stuLName = parts[i];
				} else if (val == "fName") {
					stuFName = parts[i];
				}
			}

			// create/get Student object and read in the rest of the student's
			// data
			Student stu;
			if (allStudents.containsKey(stuID)) {
				// student already exists - append info for new class
				stu = allStudents.get(stuID);
			} else {
				// new student
				stu = new Student(stuFName, stuLName, stuID);
			}

			// Read data from columns
			for (int j = 3; j < parts.length; j++) {
				if (headers.containsKey(j)) {
					String val = headers.get(j);
					if (val == "grade") {
						// 'grade' column
						stu.addClass(classID, parts[j].charAt(0));
					} else {
						// 'assignment' column
						int score = (int) (Math.round(Double.parseDouble(parts[j])));
						Assignment assign = new Assignment(val, score);
						stu.addAssignment(classID, assign);
					}
				}
			}

			clss.addStudent(stuID);
			allStudents.put(stuID, stu);
		}
		allClasses.add(clss);
	}

	/**
	 * Write student information to file specified by user.
	 */
	private static void saveStudent(String stuID, String fName) {
		if (allStudents.containsKey(stuID)) {
			// Student ID exists
			Student stu = allStudents.get(stuID);
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(fName, true));
				writer.write(stu.toString());
			} catch (IOException e1) {
				System.out.println("Invalid output file name. Please try again.");
				return;
			}

			// Close BufferedWriter
			try {
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			// Student ID doesn't exist
			System.out.println("Invalid student ID.");
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
				System.out.println("# of " + letters[i] + "'s: " + scores[i]);
			}
		}
	}

	/**
	 * The main text-based interface for the user. There are 4 commands:
	 * a - Add data from file
	 * s - Write data to file
	 * g - Query class score data
	 * e - Exit the program
	 */
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
				System.out.println("Enter the file name to read: ");
				String fName = in.next();
				fName = cleanFileName(fName);
//				if (fName.indexOf("Data/") != 0) {
//					fName = "Data/" + fName;
//				}
				System.out.println("Enter the semester (fall, spring, summer): ");
				String semester = in.next().toLowerCase().trim();
				System.out.println("Enter the year: ");
				String year = in.next().trim();
				System.out.println("Enter the course number: ");
				String cNum = in.next().trim();
				
				// Handle invalid semester input
				Set<String> seasons = new HashSet<String>();
				seasons.add("summer");
				seasons.add("fall");
				seasons.add("spring");
				if (!seasons.contains(semester)) {
					System.out.println("Invalid semester. Please enter a valid semester (summer, fall, or spring).");
					continue;
				}
				
				// Handle invalid year input
				if (!Pattern.matches("^\\d\\d\\d\\d$", year)) {
					System.out.println("Invalid year input. Please enter a valid 4-digit year.");
					continue;
				}
				
				String classID = cNum + "-" + semester + "-" + year;
				ReadData(fName, classID);
			}

			// Save data
			if (input.equals("s")) {
				System.out.println("Enter a student ID.");
				String stuID = in.next();
				System.out.println("Enter file name.");
				String fName = in.next();

				fName = cleanFileName(fName);

				saveStudent(stuID, fName);
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
