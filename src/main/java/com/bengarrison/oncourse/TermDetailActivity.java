package com.bengarrison.oncourse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class TermDetailActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 2;

    private String termId;
    private String termName;
    private String termStart;
    private String termEnd;
    private String termAction;

    private EditText addTermName;
    private EditText addTermStart;
    private EditText addTermEnd;
    private Button btnSaveTerm;
    private Button btnDeleteTerm;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainReturn) {
            Intent intent = new Intent(TermDetailActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addTermName = findViewById(R.id.etTermName);
        addTermStart = findViewById(R.id.etTermStart);
        addTermEnd = findViewById(R.id.etTermEnd);
        btnSaveTerm = findViewById(R.id.btnSaveTerm);
        btnDeleteTerm = findViewById(R.id.btnDeleteTerm);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(OnCourseProvider.CONTENT_ITEM_TYPE_TERM);
        if (uri != null) {
            termAction = Intent.ACTION_EDIT;
            String termFilter = DBOpenHelper.TERM_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.TERM_COLUMNS, termFilter, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            termId = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_ID));
            termName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_NAME));
            termStart = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_START));
            termEnd = cursor.getString(cursor.getColumnIndex(DBOpenHelper.TERM_END));
            addTermName.setText(String.valueOf(termName));
            addTermStart.setText(String.valueOf(termStart));
            addTermEnd.setText(String.valueOf(termEnd));
            ButtonMethods();
            cursor.close();
        } else {
            termAction = Intent.ACTION_INSERT;
            ButtonMethods();
        }
    }

    private void ButtonMethods() {
        btnDeleteTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> termsList = new ArrayList<>();
                String toBeAdded = null;
                int count = 0;

                Cursor courseCursor;
                courseCursor = getContentResolver().query(OnCourseProvider.CONTENT_URI_COURSES, DBOpenHelper.COURSE_COLUMNS,
                        null, null, null);
                assert courseCursor != null;
                while (true) {
                    if (!(courseCursor.moveToNext())) break;
                    toBeAdded = courseCursor.getString(courseCursor.getColumnIndex(DBOpenHelper.COURSE_TERM_NAME));
                    termsList.add(toBeAdded);
                }

                for (int i = 0; i < termsList.size(); i++) {
                    count++;
                }

                if (count != 0) {
                    Toast.makeText(TermDetailActivity.this, "Cannot delete; active courses.", Toast.LENGTH_LONG).show();
                } else {
                    deleteTerm();
                    Intent intent;
                    intent = new Intent(TermDetailActivity.this, TermListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                courseCursor.close();
            }
        });

        btnSaveTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newStart = String.valueOf(addTermStart.getText());
                String newEnd = String.valueOf(addTermEnd.getText());
                String newTermNumber = String.valueOf(addTermName.getText());

                if(termAction.equals(Intent.ACTION_EDIT)) {
                    updateTerm(newTermNumber, newStart, newEnd);
                   Intent intent = new Intent(TermDetailActivity.this, TermListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else if (termAction.equals(Intent.ACTION_INSERT)) {
                    if (!(!newTermNumber.equals("") & !newStart.equals("") & !newEnd.equals(""))) {
                         Toast.makeText(TermDetailActivity.this, "Some fields are blank. Please input missing information", Toast.LENGTH_SHORT).show();
                     } else {
                        insertTerm(newTermNumber, newStart, newEnd);
                         Intent intent = new Intent(TermDetailActivity.this, TermListActivity.class);
                         startActivityForResult(intent, REQUEST_CODE);
                    }
                }
            }
        });
    }

    public void openCourseList(View view) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void insertTerm(String termName, String startDate, String endDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.TERM_NAME, termName);
        contentValues.put(DBOpenHelper.TERM_START, startDate);
        contentValues.put(DBOpenHelper.TERM_END, endDate);
        getContentResolver().insert(OnCourseProvider.CONTENT_URI_TERMS, contentValues);
    }

    private void updateTerm(String termName, String startDate, String endDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.TERM_NAME, termName);
        contentValues.put(DBOpenHelper.TERM_START, startDate);
        contentValues.put(DBOpenHelper.TERM_END, endDate);
        String location = DBOpenHelper.TERM_ID + "= ?";
        String[] selected = {termId};
        getContentResolver().update(OnCourseProvider.CONTENT_URI_TERMS, contentValues, location, selected);
    }

    private void deleteTerm() {
        String location = DBOpenHelper.TERM_ID + "= ?";
        String[] selected = {termId};
        getContentResolver().delete(OnCourseProvider.CONTENT_URI_TERMS, location, selected);
    }

}
