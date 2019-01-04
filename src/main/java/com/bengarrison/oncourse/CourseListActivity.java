package com.bengarrison.oncourse;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class CourseListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int REQUEST_CODE = 1;
    private CursorAdapter cursorAdapter = new CourseCursorAdapter(this, null, 0);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainReturn) {
            Intent intent = new Intent(CourseListActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        if (super.onOptionsItemSelected(item)) return true;
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView courseList = findViewById(android.R.id.list);
        courseList.setAdapter(cursorAdapter);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent;
                intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                Uri uri = Uri.parse(OnCourseProvider.CONTENT_URI_COURSES + "/" + id);
                intent.putExtra(OnCourseProvider.CONTENT_ITEM_TYPE_COURSE, uri);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab;
        fab = findViewById(R.id.fabCourse);
        fab.setImageResource(R.drawable.add_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(CourseListActivity.this, CourseDetailActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, OnCourseProvider.CONTENT_URI_COURSES,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {cursorAdapter.swapCursor(cursor);}

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
