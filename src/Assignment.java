
public class Assignment {
	private String name;
	private int grade;
	private String comments;
	
	public Assignment(String name, int grade, String comments) {
		this.name = name;
		this.grade = grade;
		this.setComments(comments);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
