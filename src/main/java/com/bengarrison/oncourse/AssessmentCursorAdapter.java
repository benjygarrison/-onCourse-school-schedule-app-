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

public class AssessmentCursorAdapter extends CursorAdapter {

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(
                R.layout.assessment_list_item, viewGroup, false);
    }

    public AssessmentCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String assessmentName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.ASSESSMENT_NAME));
        String assessmentCourseNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.ASSESSMENT_COURSE_NUMBER));
        String assessmentType = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.ASSESSMENT_TYPE));

        TextView tvAssessmentName = view.findViewById(R.id.assessmentName);
        TextView tvAssessmentCourseNumber = view.findViewById(R.id.assessmentCourseNumber);
        TextView tvAssessmentType = view.findViewById(R.id.assessmentType);

        tvAssessmentName.setText(String.valueOf(assessmentName));
        tvAssessmentCourseNumber.setText(String.valueOf(assessmentCourseNumber));
        tvAssessmentType.setText(String.valueOf(assessmentType));

    }
}
