import java.util.ArrayList;

public class Class {
	private String classID;
	private String semester;
	private ArrayList<String> studentIDs = new ArrayList<String>();

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
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
}
