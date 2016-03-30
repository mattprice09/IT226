package Asg1;

import java.util.ArrayList;

public class Class {
	private String classID;
	private String className;
	private String semester;
	private String year;
	private ArrayList<String> studentIDs = new ArrayList<String>();

	// Constructor simply takes the filename (only the "226-fall-2008" part) as
	// input
	public Class(String filename) {
		setClassID(filename);
		String[] parts = filename.split("-");
		className = parts[0];
		semester = parts[1];
		setYear(parts[2]);
	}
	
	// Add student to the class' ArrayList
	public void addStudent(String stuID) {
		studentIDs.add(stuID);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getters and setters below ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public ArrayList<String> getStudents() {
		return studentIDs;
	}

	public void setStudents(ArrayList<String> students) {
		this.studentIDs = students;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
