package modelz;

public class Exam {

    private int examID;
    private String examName;
    private int subjectID;
    private int examState;

    public Exam(int examID, String examName, int examState, int subjectID) {
        this.examID = examID;
        this.examName = examName;
        this.subjectID = subjectID;
        this.examState = examState;
    }

    public Exam(String examName, int examState, int subjectID) {
        this.examName = examName;
        this.subjectID = subjectID;
        this.examState = examState;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getExamState() {
        return examState;
    }

    public void setExamState(int examState) {
        this.examState = examState;
    }
}
