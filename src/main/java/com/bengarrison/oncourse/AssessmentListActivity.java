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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;


public class AssessmentListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
        private static final int REQUEST_CODE = 1;
        private CursorAdapter cursorAdapter = new AssessmentCursorAdapter(this, null, 0);


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_assessment_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ListView list = (ListView) findViewById(android.R.id.list);
            list.setAdapter(cursorAdapter);
        //Individual on click listeners for each list time
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent;
                intent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
                Uri uri;
                uri = Uri.parse(OnCourseProvider.CONTENT_URI_ASSESSMENTS + "/" + id);
                intent.putExtra(OnCourseProvider.CONTENT_ITEM_TYPE_ASSESSMENT, uri);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

            getLoaderManager().initLoader(0, null, this);

            //FAB to create new assessment object.
            FloatingActionButton fab;
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setImageResource(R.drawable.add_icon);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent;
                    intent = new Intent(AssessmentListActivity.this, AssessmentDetailActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new CursorLoader(this, OnCourseProvider.CONTENT_URI_ASSESSMENTS,
                    null, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            cursorAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            cursorAdapter.swapCursor(null);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();

            if (id == R.id.mainReturn) {
                Intent intent = new Intent(AssessmentListActivity.this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }

            if (super.onOptionsItemSelected(item)) return true;
            else return false;
        }
}



