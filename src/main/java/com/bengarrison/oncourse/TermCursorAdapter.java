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

public class TermCursorAdapter extends CursorAdapter {

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(
                R.layout.term_list_item, viewGroup, false);
    }

    public TermCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String termName = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TERM_NAME));
        String termStartDate = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TERM_START));
        String termEndDate = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.TERM_END));

        TextView tvTermNumber = view.findViewById(R.id.tvTermList);
        TextView tvTermDates = view.findViewById(R.id.tvTermDatesList);
        tvTermNumber.setText("Term " + String.valueOf(termName));
        tvTermDates.setText(termStartDate + " - " + termEndDate);
    }
}
