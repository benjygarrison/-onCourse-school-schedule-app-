package com.bengarrison.oncourse;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//References: "Managing the Database with a Content Provider" from "Building a Note-Taking App for Android"
//(https://www.lynda.com/Android-tutorials/Managing-database-content-provider/377485/406765-4.html?org=wgu.edu),
//"Adding CRUD Methods to the Content Provider" from "Building a Note-Taking App for Android"
//(https://www.lynda.com/Android-tutorials/Adding-CRUD-methods-content-provider/377485/406766-4.html?org=wgu.edu)

public class OnCourseProvider extends ContentProvider {
    private static final String AUTHORITY = "com.bengarrison.oncourse.oncourseprovider";
    private static final String TABLE_TERMS_URI = "terms";
    private static final String TABLE_COURSES_URI = "courses";
    private static final String TABLE_ASSESSMENTS_URI = "assessments";
    public static final Uri CONTENT_URI_TERMS =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_TERMS_URI);
    public static final Uri CONTENT_URI_COURSES =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_COURSES_URI);
    public static final Uri CONTENT_URI_ASSESSMENTS =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE_ASSESSMENTS_URI);

    public static final String CONTENT_ITEM_TYPE_TERM = "Term";
    public static final String CONTENT_ITEM_TYPE_COURSE = "Course";
    public static final String CONTENT_ITEM_TYPE_ASSESSMENT = "Assessment";

    private static final int TERMS = 1;
    private static final int TERMS_ID = 2;

    private static final int COURSES = 3;
    private static final int COURSES_ID = 4;

    private static final int ASSESSMENTS = 5;
    private static final int ASSESSMENTS_ID = 6;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS_URI, TERMS);
        uriMatcher.addURI(AUTHORITY, TABLE_TERMS_URI + "/#", TERMS_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSES_URI, COURSES);
        uriMatcher.addURI(AUTHORITY, TABLE_COURSES_URI + "/#", COURSES_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS_URI, ASSESSMENTS);
        uriMatcher.addURI(AUTHORITY, TABLE_ASSESSMENTS_URI + "/#", ASSESSMENTS_ID);
    }

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] strings1, @Nullable String s1) {
        int i = uriMatcher.match(uri);
        switch (i) {
            case TERMS:
                return db.query(DBOpenHelper.TERMS_TABLE, DBOpenHelper.TERM_COLUMNS, selection, null, null, null, DBOpenHelper.TERM_ID + " ASC");
            case TERMS_ID:
                return db.query(DBOpenHelper.TERMS_TABLE, DBOpenHelper.TERM_COLUMNS, DBOpenHelper.TERM_ID + "=" + uri.getLastPathSegment(), null, null, null, DBOpenHelper.TERM_ID + " ASC");
            case COURSES:
                return db.query(DBOpenHelper.COURSES_TABLE, DBOpenHelper.COURSE_COLUMNS, selection, null, null, null, DBOpenHelper.COURSE_ID + " ASC");
            case COURSES_ID:
                return db.query(DBOpenHelper.COURSES_TABLE, DBOpenHelper.COURSE_COLUMNS, DBOpenHelper.COURSE_ID + "=" + uri.getLastPathSegment(), null, null, null, DBOpenHelper.COURSE_ID + " ASC");
            case ASSESSMENTS:
                return db.query(DBOpenHelper.ASSESSMENTS_TABLE, DBOpenHelper.ASSESSMENT_COLUMNS, selection, null, null, null, DBOpenHelper.ASSESSMENT_ID + " ASC");
            case ASSESSMENTS_ID:
                return db.query(DBOpenHelper.ASSESSMENTS_TABLE, DBOpenHelper.ASSESSMENT_COLUMNS, DBOpenHelper.ASSESSMENT_ID + "=" + uri.getLastPathSegment(), null, null, null, DBOpenHelper.ASSESSMENT_ID + " ASC");
            default:
                throw new IllegalArgumentException("Invalid query");
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {return null;}

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = 0;
        int i = uriMatcher.match(uri);
        switch (i) {
            case TERMS:
                id = db.insert(DBOpenHelper.TERMS_TABLE, null, values);
                return Uri.parse(TABLE_TERMS_URI + "/" + id);
            case COURSES:
                id = db.insert(DBOpenHelper.COURSES_TABLE, null, values);
                return Uri.parse(TABLE_COURSES_URI + "/" + id);
            case ASSESSMENTS:
                id = db.insert(DBOpenHelper.ASSESSMENTS_TABLE, null, values);
                return Uri.parse(TABLE_ASSESSMENTS_URI + "/" + id);
            default:
                throw new IllegalArgumentException("Invalid insertion");
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int i = uriMatcher.match(uri);
        switch (i) {
            case TERMS:
                return db.delete(DBOpenHelper.TERMS_TABLE, selection, selectionArgs);
            case COURSES:
                return db.delete(DBOpenHelper.COURSES_TABLE, selection, selectionArgs);
            case ASSESSMENTS:
                return db.delete(DBOpenHelper.ASSESSMENTS_TABLE, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Invalid deletion");
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int i = uriMatcher.match(uri);
        switch (i) {
            case TERMS:
                return db.update(DBOpenHelper.TERMS_TABLE, values, selection, selectionArgs);
            case COURSES:
                return db.update(DBOpenHelper.COURSES_TABLE, values, selection, selectionArgs);
            case ASSESSMENTS:
                return db.update(DBOpenHelper.ASSESSMENTS_TABLE, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Invalid update");
        }
    }
}
