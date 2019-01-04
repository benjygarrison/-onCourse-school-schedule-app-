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

public class TermListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int REQUEST_CODE = 1;
    private CursorAdapter cursorAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainReturn) {
            Intent intent = new Intent(TermListActivity.this, MainActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cursorAdapter = new TermCursorAdapter(this, null, 0);

        ListView termList = findViewById(android.R.id.list);
        termList.setAdapter(cursorAdapter);

        termList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Intent intent = new Intent(TermListActivity.this, TermDetailActivity.class);
                Uri uri = Uri.parse(OnCourseProvider.CONTENT_URI_TERMS + "/" + id);
                intent.putExtra(OnCourseProvider.CONTENT_ITEM_TYPE_TERM, uri);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        getLoaderManager().initLoader(0, null, this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.add_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermListActivity.this, TermDetailActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, OnCourseProvider.CONTENT_URI_TERMS,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {cursorAdapter.swapCursor(cursor);}

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {cursorAdapter.swapCursor(null);}
}
