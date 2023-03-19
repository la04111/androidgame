package modelz;

public class Question {

    private int questionID;
    private String questionContent;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private String answerCorrect;
    private int type;
    private int examID;

    public Question(int questionID, String questionContent, String answerOne, String answerTwo, String answerThree, String answerFour, String answerCorrect, int type, int examID) {
        this.questionID = questionID;
        this.questionContent = questionContent;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.answerCorrect = answerCorrect;
        this.type = type;
        this.examID = examID;
    }

    public Question(String questionContent, String answerOne, String answerTwo, String answerThree, String answerFour, String answerCorrect, int type, int examID) {
        this.questionContent = questionContent;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.answerCorrect = answerCorrect;
        this.type = type;
        this.examID = examID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }
}
