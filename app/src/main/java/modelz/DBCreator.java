package modelz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBCreator extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "wibu.db";
    public static final String TABLE_NAME1 = "Userz";
    public static final String TABLE_NAME2 = "Subject";
    public static final String TABLE_NAME3 = "Exam";
    public static final String TABLE_NAME4 = "Question";
    public static final String TABLE_NAME5 = "Scoreboard";
    private static int DATABASE_VERSION = 1;

    public DBCreator(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE " + TABLE_NAME1 + " (userzID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " userzName TEXT, userzPassword TEXT, userzMail TEXT, userzRole INTEGER);";
        String sql2 = "CREATE TABLE " + TABLE_NAME2 + " (subjectID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " subjectName TEXT);";
        String sql3 = "CREATE TABLE " + TABLE_NAME3 + " (examID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " examName TEXT, examState INTEGER, subjectID INTEGER, FOREIGN KEY (subjectID) REFERENCES "+TABLE_NAME2+" (subjectID));";
        String sql4 = "CREATE TABLE " + TABLE_NAME4 + " (questionID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " questionContent TEXT, answerOne TEXT, answerTwo TEXT, answerThree TEXT, answerFour TEXT, answerCorrect TEXT," +
                " type INTEGER, examID INTEGER, FOREIGN KEY (examID) REFERENCES "+TABLE_NAME3+" (examID));";
        String sql5 = "CREATE TABLE " + TABLE_NAME5 + " (userzID INTEGER NOT NULL, " +
                " examID INTEGER NOT NULL, score INTEGER, " +
                " FOREIGN KEY (userzID) REFERENCES "+TABLE_NAME1+" (userzID), " +
                " FOREIGN KEY (examID) REFERENCES "+TABLE_NAME3+" (examID), " +
                " PRIMARY KEY(userzID, examID));";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        //
       try {
           String insertAdmin = "INSERT INTO " + TABLE_NAME1 + " (userzName,userzMail, userzPassword, userzRole)"
                   +" VALUES('Admin','ad@gmail.com','123',"+1+");";
           db.execSQL(insertAdmin);
           String insertSubject1 = "INSERT INTO " + TABLE_NAME2 + " (subjectName)" +" VALUES('Toán ngẫu nhiên');";
           String insertSubject2 = "INSERT INTO " + TABLE_NAME2 + " (subjectName)" +" VALUES('Toán');";
           String insertSubject3 = "INSERT INTO " + TABLE_NAME2 + " (subjectName)" +" VALUES('Tiếng Anh');";
           String examMathRandom = "INSERT INTO " + TABLE_NAME3 + " (examName,examState,subjectID)" +" VALUES('Game 1',1,1);";
           db.execSQL(insertSubject1);
           db.execSQL(insertSubject2);
           db.execSQL(insertSubject3);
           db.execSQL(examMathRandom);
       }
       catch (Exception e) {
           Log.e("DB",e.toString());
       }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
