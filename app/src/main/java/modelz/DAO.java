package modelz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DAO {
    SQLiteDatabase db;
    DBCreator dbCreator;

    public DAO (Context context) {
        dbCreator = new DBCreator(context);
        db = dbCreator.getWritableDatabase();
    }

    //User
    public int signUp(Userz userz) {
        try {
            String sql = "INSERT INTO " + DBCreator.TABLE_NAME1 + " (userzName,userzMail, userzPassword, userzRole)"
                    +" VALUES('"+userz.getUserzName()+"','"+userz.getUserzMail()+"','"+userz.getUserzPassword()+"',"+0+");";
            db.execSQL(sql);
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    public Cursor getAllUserz() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME1, null);
        return cursor;
    }

    public Cursor findUserz(String userzName, String userzPassword) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME1
                        + " WHERE userzMail = ? and userzPassword = ?",
                        new String[]{userzName, userzPassword});
        return cursor;
    }

    //Subject
    public int insertSubject(String subjectName) {
        try {
            String sql = "INSERT INTO " + DBCreator.TABLE_NAME2 + " (subjectName)"
                    +" VALUES('"+subjectName+"');";
            db.execSQL(sql);
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    public Cursor getAllSubject() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME2, null);
        return cursor;
    }

    public Cursor findSubject(int subjectID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME2
                        + " WHERE subjectID = " + subjectID, null);
        return cursor;
    }

    public int updateSubject(int subjectID, String subjectName) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("subjectID", subjectID);
            contentValues.put("subjectName", subjectName);
            db.update(DBCreator.TABLE_NAME2,contentValues,"subjectID=?", new String[]{""+subjectID});
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    //Exam
    public int insertExam(String examName, int examState, int subjectID) {
        try {
            String sql = "INSERT INTO " + DBCreator.TABLE_NAME3 + " (examName,examState,subjectID)"
                    +" VALUES('"+examName+"',"+examState+","+subjectID+");";
            db.execSQL(sql);
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    public Cursor getAllExam() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME3, null);
        return cursor;
    }

    public Cursor getAllExamBySubjectID(int subjectID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME3 + " WHERE subjectID  = " + subjectID, null);
        return cursor;
    }

    public Cursor findExam(int examID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME3
                + " WHERE examID = " + examID, null);
        return cursor;
    }

    public int updateExam(int examID, String examName, int examState, int subjectID) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("examID", examID);
            contentValues.put("examName", examName);
            contentValues.put("examState", examState);
            contentValues.put("subjectID",subjectID);
            db.update(DBCreator.TABLE_NAME3,contentValues,"examID=?", new String[]{""+examID});
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    //Question
    public int insertQuestion(Question question) {
        try {
            String sql = "INSERT INTO " + DBCreator.TABLE_NAME4 + " (questionContent,answerOne,answerTwo,answerThree,answerFour,answerCorrect,type,examID)"
                    +" VALUES('"+question.getQuestionContent()+"','"+question.getAnswerOne()+"','"+question.getAnswerTwo()+"'," +
                    "'"+question.getAnswerThree()+"','"+question.getAnswerFour()+"','"+question.getAnswerCorrect()+"'," +
                    ""+question.getType()+","+question.getExamID()+");";
            db.execSQL(sql);
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    public Cursor getAllQuestionByExamID(int examID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME4 + " WHERE examID = ?", new String[]{""+examID});
        return cursor;
    }

    public Cursor findQuestion(int questionID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME4
                + " WHERE questionID = " + questionID, null);
        return cursor;
    }

    public int updateQuestion(Question question) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("questionID", question.getQuestionID());
            contentValues.put("questionContent", question.getQuestionContent());
            contentValues.put("answerOne", question.getAnswerOne());
            contentValues.put("answerTwo",question.getAnswerTwo());
            contentValues.put("answerThree", question.getAnswerThree());
            contentValues.put("answerFour", question.getAnswerFour());
            contentValues.put("answerCorrect", question.getAnswerCorrect());
            contentValues.put("type",question.getType());
            contentValues.put("examID",question.getExamID());
            db.update(DBCreator.TABLE_NAME4,contentValues,"questionID=?", new String[]{""+question.getQuestionID()});
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    //Score
    public int insertScoreBoard(int userzID, int examID, int score) {
        try {
            String sql = "INSERT INTO " + DBCreator.TABLE_NAME5 + " (userzID,examID,score)"
                    +" VALUES('"+userzID+"',"+examID+","+score+");";
            db.execSQL(sql);
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }


    public Cursor findScoreBoard1(int userzID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME5
                + " WHERE userzID = " + userzID, null);
        return cursor;
    }

    public Cursor findScoreBoard2(int userzID, int examID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME5
                + " WHERE userzID = " + userzID + " and examID = " + examID, null);
        return cursor;
    }

    public int updateScoreBoard(int userzID, int examID, int score) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userzID", userzID);
            contentValues.put("examID", examID);
            contentValues.put("score", score);
            db.update(DBCreator.TABLE_NAME5,contentValues,"userzID = ? and examID = ? ", new String[]{""+userzID,""+examID});
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }
    //
    //
    public Cursor findUserz(String userszEmails) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME1 + " WHERE userzMail = " + "'"+userszEmails+"'", null);
        return cursor;
    }

    public Cursor findUserzByID(int userzID) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBCreator.TABLE_NAME1 + " WHERE userzID = " + userzID, null);
        return cursor;
    }

    public int updateUsersz(Userz use) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("userzID", use.getUserzID());
            contentValues.put("userzName", use.getUserzName());
            contentValues.put("userzPassword", use.getUserzPassword());
            contentValues.put("userzMail", use.getUserzMail());
            contentValues.put("userzRole", use.getUserzRole());
            db.update(DBCreator.TABLE_NAME1,contentValues,"userzID=?", new String[]{""+use.getUserzID()});
            return 1;
        }
        catch (Exception e) {
            Log.e("DB",e.toString());
            return -1;
        }
    }

    public void close() {
        db.close();
    }
}
