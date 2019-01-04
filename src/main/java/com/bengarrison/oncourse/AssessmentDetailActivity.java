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

public class AssessmentDetailActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 2;


    private String assessmentId;
    private String courseNumber;
    private String assessmentName;
    private String assessmentType;
    private String assessmentGoalDate;
    private String assessmentAction;
    private String assessmentTarget;
    private String[] courses;

    private EditText addCourseNumber;
    private EditText addAssessmentName;
    private EditText addAssessmentType;
    private EditText addAssessmentGoalDate;
    private Button btnSaveAssessment;
    private Button btnDeleteAssessment;
    private Button btnSetAlert15Sec;
    private Button btnSetAlert1day;
    private Button btnSetAlert1week;
    private Button btnSetAlert1month;

    //String[] alertDateArray = new String[]{"15 seconds", "1 day", "1 week", "1 month", "2 months",
    //"3 months", "4 months", "5 months", "6 months"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainReturn) {
            Intent intent = new Intent(AssessmentDetailActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(OnCourseProvider.CONTENT_ITEM_TYPE_ASSESSMENT);


        btnSaveAssessment = findViewById(R.id.btnSaveAssessment);
        btnDeleteAssessment = findViewById(R.id.btnDeleteAssessment);
        btnSetAlert15Sec = findViewById(R.id.btnSetStartNotification15sec);

        if (uri != null) {
            assessmentAction = Intent.ACTION_EDIT;
            assessmentTarget = DBOpenHelper.ASSESSMENT_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri,
                DBOpenHelper.ASSESSMENT_COLUMNS, assessmentTarget, null, null);
            cursor.moveToFirst();

            assessmentId = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_ID));
            assessmentName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_NAME));
            assessmentType = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_TYPE));
            courseNumber = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_COURSE_NUMBER));
            assessmentGoalDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.ASSESSMENT_DATE));

            addAssessmentName = findViewById(R.id.etAssessmentName);
            addAssessmentType = findViewById(R.id.etAssessmentType);
            addCourseNumber = findViewById(R.id.etCourseName);
            addAssessmentGoalDate = findViewById(R.id.etAssessmentDueDate);

            addAssessmentName.setText(assessmentName);
            addAssessmentType.setText(assessmentType);
            addCourseNumber.setText(courseNumber);
            addAssessmentGoalDate.setText(assessmentGoalDate);
            ButtonMethods();
            addCourseDialogMethod();
        } else {
            assessmentAction = Intent.ACTION_INSERT;
            addAssessmentName = findViewById(R.id.etAssessmentName);
            addAssessmentType = findViewById(R.id.etAssessmentType);
            addCourseNumber = findViewById(R.id.etCourseName);
            addAssessmentGoalDate = findViewById(R.id.etAssessmentDueDate);
            ButtonMethods();
            addCourseDialogMethod();
        }
    }

    private void ButtonMethods() {
        btnSaveAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newCourseNumber = String.valueOf(addCourseNumber.getText());
                String newAssessmentName = String.valueOf(addAssessmentName.getText());
                String newAssessmentType = String.valueOf(addAssessmentType.getText());
                String newGoalDate = String.valueOf(addAssessmentGoalDate.getText());

                if (assessmentAction == Intent.ACTION_EDIT) {
                    updateAssessment(newAssessmentName, newAssessmentType, newCourseNumber, newGoalDate);
                    Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    if (assessmentAction != Intent.ACTION_INSERT) {
                        return;
                    }
                    insertAssessment(newAssessmentName, newAssessmentType, newCourseNumber, newGoalDate);
                    Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentListActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);

                }
            }
        });

        btnDeleteAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAssessment();
                Intent intent;
                intent = new Intent(AssessmentDetailActivity.this, AssessmentListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnSetAlert15Sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentAlarm15Sec();
            }
        }); }

    private void addCourseDialogMethod() {

        addCourseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    ArrayList<String> courseList = new ArrayList<>();
                    String toBeAdded = null;

                    Cursor courseCursor;
                    courseCursor = getContentResolver().query(OnCourseProvider.CONTENT_URI_COURSES, DBOpenHelper.COURSE_COLUMNS,
                        null, null, null);
                while (true) {
                    if ((courseCursor.moveToNext())) {
                        toBeAdded = courseCursor.getString(courseCursor.getColumnIndex(DBOpenHelper.COURSE_COURSE_NUMBER));
                        courseList.add(toBeAdded);
                    } else {
                        break;
                    }
                    }

                    AssessmentDetailActivity.this.courses = new String[courseList.size()];

                    for (int i = 0; i < courseList.size(); i++) {
                        AssessmentDetailActivity.this.courses[i] = courseList.get(i);
                    }

                if (AssessmentDetailActivity.this.courses.length != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AssessmentDetailActivity.this);
                    builder.setTitle("Select course:");
                    builder.setItems(AssessmentDetailActivity.this.courses, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String newCourseTerm = AssessmentDetailActivity.this.courses[i];
                            addCourseNumber.setText(newCourseTerm);
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(AssessmentDetailActivity.this, "Please create a course first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*  The alarm system found below is basically pointless, but was a required component of the
        application to pass the course.
        The alarm itself is set to 15 seconds for the convenience of the evaluator testing the function.
        It does not function in Android 9, but should function in lower versions (which this code was written for).
     */

    public void assessmentAlarm15Sec() {
        Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentGoalAlarm.class);
        PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(),0, intent,0);
        AlarmManager a = (AlarmManager)getSystemService(ALARM_SERVICE);
        a.set(AlarmManager.RTC,System.currentTimeMillis() + (15 * 1000), p1);
    }


    private void insertAssessment(String newTestName, String newTestType, String newCourseNum, String newDate) {
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.ASSESSMENT_NAME, newTestName);
        contentValues.put(DBOpenHelper.ASSESSMENT_TYPE, newTestType);
        contentValues.put(DBOpenHelper.ASSESSMENT_COURSE_NUMBER, newCourseNum);
        contentValues.put(DBOpenHelper.ASSESSMENT_DATE, newDate);
        getContentResolver().insert(OnCourseProvider.CONTENT_URI_ASSESSMENTS, contentValues);
    }

    private void updateAssessment(String newTestName, String newTestType, String newCourseNum, String newDate) {
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put(DBOpenHelper.ASSESSMENT_NAME, newTestName);
        contentValues.put(DBOpenHelper.ASSESSMENT_TYPE, newTestType);
        contentValues.put(DBOpenHelper.ASSESSMENT_COURSE_NUMBER, newCourseNum);
        contentValues.put(DBOpenHelper.ASSESSMENT_DATE, newDate);
        String location = DBOpenHelper.ASSESSMENT_ID + "= ?";
        String[] selected = {assessmentId};
        getContentResolver().update(OnCourseProvider.CONTENT_URI_ASSESSMENTS, contentValues, location, selected);
    }

    private void deleteAssessment() {
        String location = DBOpenHelper.ASSESSMENT_ID + "= ?";
        String[] selected = {assessmentId};
        getContentResolver().delete(OnCourseProvider.CONTENT_URI_ASSESSMENTS, location, selected);
    }
}
