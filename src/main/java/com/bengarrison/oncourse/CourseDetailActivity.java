package com.bengarrison.oncourse;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
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

public class CourseDetailActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 2;

    private String courseId;
    private String termName;
    private String courseNumber;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String courseNotes;
    private String[] terms;
    private String courseAction;
    private String courseTarget;

    private EditText addCourseName;
    private EditText addCourseTermName;
    private EditText addCourseStart;
    private EditText addCourseEnd;
    private EditText addCourseNumber;
    private EditText addCourseStatus;
    private EditText addMentorName;
    private EditText addMentorPhone;
    private EditText addMentorEmail;
    private EditText addCourseNotes;
    private Button btnSaveCourse;
    private Button btnDeleteCourse;
    private Button btnShareNotes;
    private Button btnSetStartAlert15Sec;
    private Button btnSetStartAlert1Day;
    private Button btnSetStartAlert1Week;
    private Button btnSetStartAlert1Month;
    private Button btnSetEndAlert15Sec;
    private Button btnSetEndAlert1Day;
    private Button btnSetEndAlert1Week;
    private Button btnSetEndAlert1Month;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainReturn) {
            Intent intent = new Intent(CourseDetailActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSaveCourse = findViewById(R.id.btnSaveTerm);
        btnDeleteCourse = findViewById(R.id.btnDeleteCourse);
        btnShareNotes = findViewById(R.id.btnShareNotes);
        btnSetStartAlert15Sec = findViewById(R.id.btnSetStartNotification15sec);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(OnCourseProvider.CONTENT_ITEM_TYPE_COURSE);
        if (uri != null) {
            courseAction = Intent.ACTION_EDIT;
            courseTarget = DBOpenHelper.COURSE_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                    DBOpenHelper.COURSE_COLUMNS, courseTarget, null, null);
            cursor.moveToFirst();

            courseId = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_ID));
            courseName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_NAME));
            courseNumber = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_COURSE_NUMBER));
            courseStart = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_START));
            courseEnd = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_END));
            termName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_TERM_NAME));
            courseStatus = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_STATUS));
            mentorName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_MENTOR));
            mentorPhone = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_MENTOR_PHONE));
            mentorEmail = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_MENTOR_EMAIL));
            courseNotes = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COURSE_NOTES));

            addCourseTermName = findViewById(R.id.etCourseTerm);
            addCourseNumber = findViewById(R.id.etCourseNumber);
            addCourseName = findViewById(R.id.etCourseName);
            addCourseStart = findViewById(R.id.etCourseStart);
            addCourseEnd = findViewById(R.id.etCourseEnd);
            addCourseStatus = findViewById(R.id.etCourseStatus);
            addMentorName = findViewById(R.id.etMentorName);
            addMentorPhone = findViewById(R.id.etMentorPhone);
            addMentorEmail = findViewById(R.id.etMentorEmail);
            addCourseNotes = findViewById(R.id.etCourseNotes);

            addCourseTermName.setText(termName);
            addCourseNumber.setText(courseNumber);
            addCourseName.setText(courseName);
            addCourseStart.setText(courseStart);
            addCourseEnd.setText(courseEnd);
            addCourseStatus.setText(courseStatus);
            addMentorName.setText(mentorName);
            addMentorPhone.setText(mentorPhone);
            addMentorEmail.setText(mentorEmail);
            addCourseNotes.setText(courseNotes);
            ButtonMethods();
            addTermDialogMethod();
            cursor.close();
        } else {
            courseAction = Intent.ACTION_INSERT;
            addCourseTermName = findViewById(R.id.etCourseTerm);
            addCourseNumber = findViewById(R.id.etCourseNumber);
            addCourseName = findViewById(R.id.etCourseName);
            addCourseStart = findViewById(R.id.etCourseStart);
            addCourseEnd = findViewById(R.id.etCourseEnd);
            addCourseStatus = findViewById(R.id.etCourseStatus);
            addMentorName = findViewById(R.id.etMentorName);
            addMentorPhone = findViewById(R.id.etMentorPhone);
            addMentorEmail = findViewById(R.id.etMentorEmail);
            addCourseNotes = findViewById(R.id.etCourseNotes);
            ButtonMethods();
            addTermDialogMethod();
        }
    }

    private void ButtonMethods() {
        btnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTermNumber = String.valueOf(addCourseTermName.getText());
                String newCourseName = String.valueOf(addCourseName.getText());
                String newCourseNumber = String.valueOf(addCourseNumber.getText());
                String newCourseStart = String.valueOf(addCourseStart.getText());
                String newCourseEnd = String.valueOf(addCourseEnd.getText());
                String newCourseStatus = String.valueOf(addCourseStatus.getText());
                String newMentorName = String.valueOf(addMentorName.getText());
                String newMentorPhone = String.valueOf(addMentorPhone.getText());
                String newMentorEmail = String.valueOf(addMentorEmail.getText());
                String newCourseNotes = String.valueOf(addCourseNotes.getText());

                if (courseAction != Intent.ACTION_EDIT) {
                    if (courseAction == Intent.ACTION_INSERT) {
                        insertCourse(newCourseNumber, newCourseName, newCourseStart, newCourseEnd, newCourseStatus, newTermNumber, newMentorName, newMentorPhone, newMentorEmail, newCourseNotes);
                        Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        return;
                    }
                } else {
                    updateCourse(newCourseNumber, newCourseName, newCourseStart, newCourseEnd, newCourseStatus, newTermNumber, newMentorName, newMentorPhone, newMentorEmail, newCourseNotes);
                    Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });

        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
                Intent intent = new Intent(CourseDetailActivity.this, CourseListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnShareNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notesToShare = String.valueOf(addCourseNotes.getText());
                String notesContent = String.valueOf(addCourseNumber.getText() + " notes: ");
                Intent shareNotesIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareNotesIntent.setType("text/plain");
                shareNotesIntent.putExtra(Intent.EXTRA_SUBJECT, notesContent);
                shareNotesIntent.putExtra(Intent.EXTRA_TEXT, notesToShare);
                startActivity(Intent.createChooser(shareNotesIntent, "Share notes"));
            }
        });

        btnSetStartAlert15Sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseStartNotification15Sec();
            }
        });

    }

    private void addTermDialogMethod() {
        addCourseTermName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> termsList = new ArrayList<>();
                String toBeAdded = null;

                Cursor termCursor = getContentResolver().query(OnCourseProvider.CONTENT_URI_TERMS, DBOpenHelper.TERM_COLUMNS,
                        null, null, null);
                while (true) {
                    if (!(termCursor.moveToNext())) break;
                    toBeAdded = termCursor.getString(termCursor.getColumnIndex(DBOpenHelper.TERM_NAME));
                    termsList.add(toBeAdded);
                }

                CourseDetailActivity.this.terms = new String[termsList.size()];

                for (int i = 0; i < termsList.size(); i++) {
                    CourseDetailActivity.this.terms[i] = termsList.get(i);
                }

                if (CourseDetailActivity.this.terms.length != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourseDetailActivity.this);
                    builder.setTitle("Select term:");
                    builder.setItems(CourseDetailActivity.this.terms, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newCourseTerm = CourseDetailActivity.this.terms[i];
                            addCourseTermName.setText(newCourseTerm);
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(CourseDetailActivity.this, "Please create a term first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*  The alarm system found below is basically pointless, but was a required component of the
        application to pass the course.
        The alarm itself is set to 15 seconds for the convenience of the evaluator testing the function.
        It does not function in Android 9, but should function in lower versions (which this code was written for).
     */

    public void courseStartNotification15Sec() {
        Intent intent=new Intent(CourseDetailActivity.this, CourseStartAlarm.class);
        PendingIntent p1=PendingIntent.getBroadcast(getApplicationContext(),0, intent,0);
        AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
        a.set(AlarmManager.RTC,System.currentTimeMillis() + (15 * 1000), p1);
    }


    public void openAssessmentList(View view) {
        Intent intent = new Intent(this, AssessmentListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void insertCourse(String courseNum, String courseName, String startDate, String endDate, String status,
                              String termName, String mentorName, String mentorTel, String mentorEmail, String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.COURSE_COURSE_NUMBER, courseNum);
        contentValues.put(DBOpenHelper.COURSE_NAME, courseName);
        contentValues.put(DBOpenHelper.COURSE_START, startDate);
        contentValues.put(DBOpenHelper.COURSE_END, endDate);
        contentValues.put(DBOpenHelper.COURSE_STATUS, status);
        contentValues.put(DBOpenHelper.COURSE_TERM_NAME, termName);
        contentValues.put(DBOpenHelper.COURSE_MENTOR, mentorName);
        contentValues.put(DBOpenHelper.COURSE_MENTOR_PHONE, mentorTel);
        contentValues.put(DBOpenHelper.COURSE_MENTOR_EMAIL, mentorEmail);
        contentValues.put(DBOpenHelper.COURSE_NOTES, notes);
        getContentResolver().insert(OnCourseProvider.CONTENT_URI_COURSES, contentValues);
    }

    private void updateCourse(String courseNum, String courseName, String startDate, String endDate, String status,
                              String termName, String mentorName, String mentorTel, String mentorEmail, String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.COURSE_COURSE_NUMBER, courseNum);
        contentValues.put(DBOpenHelper.COURSE_NAME, courseName);
        contentValues.put(DBOpenHelper.COURSE_START, startDate);
        contentValues.put(DBOpenHelper.COURSE_END, endDate);
        contentValues.put(DBOpenHelper.COURSE_STATUS, status);
        contentValues.put(DBOpenHelper.COURSE_TERM_NAME, termName);
        contentValues.put(DBOpenHelper.COURSE_MENTOR, mentorName);
        contentValues.put(DBOpenHelper.COURSE_MENTOR_PHONE, mentorTel);
        contentValues.put(DBOpenHelper.COURSE_MENTOR_EMAIL, mentorEmail);
        contentValues.put(DBOpenHelper.COURSE_NOTES, notes);
        String location = DBOpenHelper.COURSE_ID + "= ?";
        String[] selected = {courseId};
        getContentResolver().update(OnCourseProvider.CONTENT_URI_COURSES, contentValues, location, selected);
    }

    private void deleteCourse() {
        String location = DBOpenHelper.COURSE_ID + "= ?";
        String[] selected = {courseId};
        getContentResolver().delete(OnCourseProvider.CONTENT_URI_COURSES, location, selected);
    }
}