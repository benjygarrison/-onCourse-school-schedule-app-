package com.bengarrison.oncourse;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

//Reference: 'Android CursorAdapter with custom layout and how to use it'
// https://coderwall.com/p/fmavhg/android-cursoradapter-with-custom-layout-and-how-to-use-it

public class CourseCursorAdapter extends CursorAdapter{

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(
                R.layout.course_list_item, viewGroup, false);
    }

    public CourseCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String courseTermName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_TERM_NAME));
        String courseNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_COURSE_NUMBER));
        String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_NAME));
        String courseStartDate = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_START));
        String courseEndDate = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_END));
        String courseStatus = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.COURSE_STATUS));
        TextView tvCourseName = view.findViewById(R.id.tvCourseList);
        TextView tvCourseDates = view.findViewById(R.id.tvCourseDateList);
        TextView tvCourseStatus = view.findViewById(R.id.tvCourseStatus);

        String courseDetails = "Term " + String.valueOf(courseTermName) + ": " + String.valueOf(courseNumber) + " " + String.valueOf(courseName);
        String courseDates = courseStartDate + " - " + courseEndDate;
        String statusOfCourse = "Status: " + String.valueOf(courseStatus);
        tvCourseName.setText(courseDetails);
        tvCourseDates.setText(courseDates);
        tvCourseStatus.setText(statusOfCourse);
    }
}
