package modelz;

public class Scoreboard {

    private int userzID;
    private int examID;
    private int score;

    public Scoreboard(int userzID, int examID, int score) {
        this.userzID = userzID;
        this.examID = examID;
        this.score = score;
    }

    public int getUserzID() {
        return userzID;
    }

    public void setUserzID(int userzID) {
        this.userzID = userzID;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
