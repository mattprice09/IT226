import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student {
	private String fName;
	private String lName;
	private String id;
	private Map<String, Character> classGrades = new HashMap<String, Character>();
	private Map<String, ArrayList<Assignment>> classAssignments = new HashMap<String, ArrayList<Assignment>>();

	public Student(String fname, String lname, String id) {
		this.fName = fname;
		this.lName = lname;
		this.id = id;
	}

	// Add a class to the student's map
	public void addClass(String className, char grade) {
		classGrades.put(className, grade);
	}

	// Get the grade for the input class. Returns null if class name doesn't
	// exist.
	public Character getClassGrade(String className) {
		return this.classGrades.get(className);
	}

	// Add input assignment to ArrayList of assignments for the class with the
	// specified name
	public void addAssignment(String className, Assignment assignment) {
		ArrayList<Assignment> assigns = new ArrayList<Assignment>();

		// check if list of specified class' assignments already exists
		if (classAssignments.containsKey(className)) {
			assigns = classAssignments.get(className);
		}
		assigns.add(assignment);
		classAssignments.put(className, assigns);
	}

	// Get the list of assignments for the specified class. Returns null if
	// class name is invalid.
	public ArrayList<Assignment> getClassAssignments(String className) {
		return this.classAssignments.get(className);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~ Getters and setters below ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public Map<String, ArrayList<Assignment>> getClassAssignments() {
		return classAssignments;
	}

	public void setClassAssignments(Map<String, ArrayList<Assignment>> classAssignments) {
		this.classAssignments = classAssignments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setClassGrades(Map<String, Character> classGrades) {
		this.classGrades = classGrades;
	}

	public Map<String, Character> getClassGrades() {
		return classGrades;
	}
}
