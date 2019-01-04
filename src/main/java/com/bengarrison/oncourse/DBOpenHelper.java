package com.bengarrison.oncourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Reference: "Defining the SQLite Database Structure" from "Building a Note-Taking App"
//(https://www.lynda.com/Android-tutorials/Defining-SQLite-database-structure/377485/406764-4.html?org=wgu.edu)

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "oncourse.db";
    private static final int DATABASE_VERSION = 2;

    //Terms table
    public static final String TERMS_TABLE = "terms";
    public static final String TERM_ID = "_id";
    public static final String TERM_NAME = "termName";
    public static final String TERM_START = "startDate";
    public static final String TERM_END = "endDate";

    public static final String[] TERM_COLUMNS = {TERM_ID, TERM_NAME, TERM_START, TERM_END};

    //Create terms table
    private static final String TERMS_TABLE_CREATE =
            "CREATE TABLE " + TERMS_TABLE + " (" +
                    TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_NAME + " TEXT, " +
                    TERM_START + " TEXT, " +
                    TERM_END + " TEXT" + ")";

    //Courses table
    public static final String COURSES_TABLE = "courses";
    public static final String COURSE_ID = "_id";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_COURSE_NUMBER = "courseNum";
    public static final String COURSE_START = "startDate";
    public static final String COURSE_END = "endDate";
    public static final String COURSE_TERM_NAME = "termName";
    public static final String COURSE_STATUS = "courseStatus";
    public static final String COURSE_MENTOR = "mentorName";
    public static final String COURSE_MENTOR_PHONE = "mentorTel";
    public static final String COURSE_MENTOR_EMAIL = "mentorEmail";
    public static final String COURSE_NOTES = "courseNotes";

    public static final String[] COURSE_COLUMNS =
            {COURSE_ID, COURSE_NAME, COURSE_COURSE_NUMBER, COURSE_START, COURSE_END, COURSE_TERM_NAME,
                    COURSE_STATUS, COURSE_MENTOR, COURSE_MENTOR_PHONE, COURSE_MENTOR_EMAIL, COURSE_NOTES};

    //Create courses table
    private static final String COURSES_TABLE_CREATE =
            "CREATE TABLE " + COURSES_TABLE + "(" +
                    COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_NAME + " TEXT, " +
                    COURSE_COURSE_NUMBER + " TEXT, " +
                    COURSE_START + " TEXT, " +
                    COURSE_END + " TEXT, " +
                    COURSE_TERM_NAME + " TEXT, " +
                    COURSE_STATUS + " TEXT," +
                    COURSE_MENTOR + " TEXT," +
                    COURSE_MENTOR_PHONE + " TEXT," +
                    COURSE_MENTOR_EMAIL + " TEXT," +
                    COURSE_NOTES + " TEXT" + ")";

    //Assessments table
    public static final String ASSESSMENTS_TABLE = "assessments";
    public static final String ASSESSMENT_ID = "_id";
    public static final String ASSESSMENT_NAME = "assessmentName";
    public static final String ASSESSMENT_TYPE = "assessmentType";
    public static final String ASSESSMENT_COURSE_NUMBER = "assessmentCourseNum";
    public static final String ASSESSMENT_DATE = "assessmentDueDate";

    public static final String[] ASSESSMENT_COLUMNS =
            {ASSESSMENT_ID, ASSESSMENT_NAME, ASSESSMENT_TYPE, ASSESSMENT_COURSE_NUMBER, ASSESSMENT_DATE};

    //Create assessments table
    private static final String ASSESSMENT_TABLE_CREATE =
            "CREATE TABLE " + ASSESSMENTS_TABLE + " (" +
                    ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_NAME + " TEXT, " +
                    ASSESSMENT_TYPE + " TEXT, " +
                    ASSESSMENT_COURSE_NUMBER + " TEXT," +
                    ASSESSMENT_DATE + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TERMS_TABLE_CREATE);
        db.execSQL(COURSES_TABLE_CREATE);
        db.execSQL(ASSESSMENT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TERMS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ASSESSMENTS_TABLE);
    }

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
