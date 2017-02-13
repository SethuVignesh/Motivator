package com.newtra.motivator.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newtra.motivator.R;
import com.newtra.motivator.data.MotivatorContract;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private Cursor mDetailCursor;
    private View mRootView;
    private int mPosition;
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mTextViewTitle;
    private TextView mUriText;
    private Uri mUri;
    private static final int CURSOR_LOADER_ID = 0;


//    public static DetailActivity newInstance(int position, Uri uri) {
//        DetailActivity fragment = new DetailActivity();
//        Bundle args = new Bundle();
//        fragment.mPosition = position;
//        fragment.mUri = uri;
//        args.putInt("id", position);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public DetailActivity() {
        // Required empty public constructor
    }

    Toolbar myToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        setContentView(R.layout.fragment_detail);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        mImageView = (ImageView) findViewById(R.id.flavor_icon);
        mTextView = (TextView) findViewById(R.id.version_description);
        mTextViewTitle = (TextView) findViewById(R.id.textViewTitle);
        mUriText = (TextView) findViewById(R.id.uri);
        Bundle args = this.getIntent().getExtras();
        mUri=(Uri)(args.get("uriId"));
        getLoaderManager().initLoader(CURSOR_LOADER_ID, args, this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        String selection = null;
        String[] selectionArgs = null;
        if (args != null){
            selection = MotivatorContract.FlavorEntry._ID;
            selectionArgs = new String[]{String.valueOf(mPosition)};
        }
        return new CursorLoader(DetailActivity.this,
                mUri,
                null,
                selection,
                selectionArgs,
                null);
    }


    // Set the cursor in our CursorAdapter once the Cursor is loaded
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDetailCursor = data;
        mDetailCursor.moveToFirst();
        DatabaseUtils.dumpCursor(data);
        mImageView.setImageResource(mDetailCursor.getInt(3));
        mTextView.setText(mDetailCursor.getString(2));
        mTextViewTitle.setText(mDetailCursor.getString(1));
        myToolbar.setTitle(mDetailCursor.getString(1));
        // set Uri to be displayed
        mUriText.setText(mUri.toString());
    }

    // reset CursorAdapter on Loader Reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mDetailCursor = null;
    }

}
