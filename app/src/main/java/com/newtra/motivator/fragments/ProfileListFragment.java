package com.newtra.motivator.fragments;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.newtra.motivator.Beans.Flavor;
import com.newtra.motivator.R;
import com.newtra.motivator.activity.DetailActivity;
import com.newtra.motivator.adapters.FlavorAdapter;
import com.newtra.motivator.data.MotivatorContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = ProfileListFragment.class.getSimpleName();

    private FlavorAdapter mFlavorAdapter;
    private GridView mGridView;


    private static final int CURSOR_LOADER_ID = 0;
    // Static values for our flavors
    // content credit https://www.android.com/intl/en_us/history/
    Flavor[] flavors = {
            new Flavor("Steve Jobs", "Steven Paul \"Steve\" Jobs (/ˈdʒɒbz/; February 24, 1955 – October 5, 2011) was an American businessman, inventor, and industrial designer. He was the co-founder, chairman, and chief executive officer (CEO) of Apple Inc.; CEO and majority shareholder of Pixar;[2] a member of The Walt Disney Company's board of directors following its acquisition of Pixar; and founder, chairman, and CEO of NeXT. Jobs is widely recognized as a pioneer of the microcomputer revolution of the 1970s and 1980s, along with Apple co-founder Steve Wozniak.", R.drawable.stevejobs),
            new Flavor("Will Smith", "Willard Carroll \"Will\" Smith Jr. (born September 25, 1968) is an American actor, producer, rapper, and songwriter. In April 2007, Newsweek called him \"the most powerful actor in Hollywood\".[3] Smith has been nominated for five Golden Globe Awards and two Academy Awards, and has won four Grammy Awards.",
                    R.drawable.willsmith),
            new Flavor("Bruce Lee", "Lee Jun-fan (Chinese: 李振藩; November 27, 1940 – July 20, 1973), known professionally as Bruce Lee, was a Hong Kong and American actor, martial artist, philosopher, filmmaker,[2] and founder of the martial art Jeet Kune Do. Lee was the son of Cantonese opera star Lee Hoi-Chuen. He is widely considered by commentators, critics, media, and other martial artists to be one of the most influential martial artists of all time,[3] and a pop culture icon of the 20th century.[4][5] He is often credited with helping to change the way Asians were presented in American films.", R.drawable.brucelee),
            new Flavor("Brian Tracy", "Brian Tracy (born 5 January 1944)[2] is a Canadian-born American motivational public speaker and self-development author.[3][4][5] He is the author of over 70 books that have been translated into dozens of languages.[6] His popular books are Earn What You’re Really Worth,[7] Eat That Frog!,[8] and The Psychology of Achievement.[9] As an author, he has been largely collected by libraries worldwide.", R.drawable.briantracy),
            new Flavor("Arnold", "Arnold Alois Schwarzenegger (/ˈʃwɔːrtsənˌɛɡər/; German: [ˈaɐ̯nɔlt ˈalɔʏs ˈʃvaɐ̯tsn̩ˌɛɡɐ]; born July 30, 1947) is an Austrian-American actor, producer, businessman, investor, author, philanthropist, activist, and former professional bodybuilder and politician. He served two terms as the 38th Governor of California from 2003 until 2011.", R.drawable.arnold),

    };

    public ProfileListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Cursor c =
            getActivity().getContentResolver().query(MotivatorContract.FlavorEntry.CONTENT_URI,
            new String[]{MotivatorContract.FlavorEntry._ID},
                    null,
                    null,
                    null);
        if (c.getCount() == 0){
            insertData();
        }
        // initialize loader
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate fragment_main layout
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // initialize our FlavorAdapter
        mFlavorAdapter = new FlavorAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);
        // initialize mGridView to the GridView in fragment_main.xml
        mGridView = (GridView) rootView.findViewById(R.id.flavors_grid);


        return rootView;
    }

    // insert data into database
    public void insertData(){
        ContentValues[] flavorValuesArr = new ContentValues[flavors.length];
        // Loop through static array of Flavors, add each to an instance of ContentValues
        // in the array of ContentValues
        for(int i = 0; i < flavors.length; i++){
            flavorValuesArr[i] = new ContentValues();
            flavorValuesArr[i].put(MotivatorContract.FlavorEntry.COLUMN_ICON, flavors[i].image);
            flavorValuesArr[i].put(MotivatorContract.FlavorEntry.COLUMN_VERSION_NAME,
                    flavors[i].name);
            flavorValuesArr[i].put(MotivatorContract.FlavorEntry.COLUMN_DESCRIPTION,
                    flavors[i].description);
        }

        // bulkInsert our ContentValues array
        getActivity().getContentResolver().bulkInsert(MotivatorContract.FlavorEntry.CONTENT_URI,
                flavorValuesArr);
    }

    // Attach loader to our flavors database query
    // run when loader is initialized
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        return new CursorLoader(getActivity(),
                MotivatorContract.FlavorEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    // Set the cursor in our CursorAdapter once the Cursor is loaded
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mFlavorAdapter.swapCursor(data);

        // set mGridView adapter to our CursorAdapter
        mGridView.setAdapter(mFlavorAdapter);

        // make each item clickable
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // increment the position to match Database Ids indexed starting at 1
                int uriId = position + 1;
                // append Id to uri
                Uri uri = ContentUris.withAppendedId(MotivatorContract.FlavorEntry.CONTENT_URI,
                        uriId);
                // create fragment
//                DetailActivity detailFragment = DetailActivity.newInstance(uriId, uri);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, detailFragment)
//                        .addToBackStack(null).commit();
                Intent intent= new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("uriId",uri);
                startActivity(intent);
            }
        });



    }

    // reset CursorAdapter on Loader Reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mFlavorAdapter.swapCursor(null);
    }
}
