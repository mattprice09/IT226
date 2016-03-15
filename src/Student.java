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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Character getClassGrade(String className) {
		return this.classGrades.get(className);
	}

	public Map<String, Character> getClassGrades() {
		return classGrades;
	}
}
