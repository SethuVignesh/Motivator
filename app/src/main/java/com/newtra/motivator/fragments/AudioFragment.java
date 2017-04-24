package com.newtra.motivator.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.newtra.motivator.Constants2;
import com.newtra.motivator.NotificationService;
import com.newtra.motivator.audiomoduletemp.RefreshAudioListView;
import com.newtra.motivator.audiomoduletemp.DownloadAudio;
import com.newtra.motivator.audiomoduletemp.RefreshAudioListView;
import com.newtra.motivator.beanobject.Audio;
import com.newtra.motivator.R;
import com.newtra.motivator.Utility;
import com.newtra.motivator.adapters.AudioListAdapter;
import com.newtra.motivator.data.DemoData;
import com.newtra.motivator.data.MotivatorContract;
import com.newtra.motivator.data.MotivatorDBHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class AudioFragment extends Fragment implements RefreshAudioListView {
    public static final String STEVEJOBS = "stevejobs";
    public static final String ARNOLD = "arnold";
    public static final String WILLSMITH = "willsmith";
    public static final String BRUCELEE = "brucelee";
    public static final String BRIAN = "brian";
    ListView booksListView;
    private ProgressDialog mProgressDialog;
    public static String selectedLanguage;

    public AudioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.activity_main2, container, false);
        booksListView = (ListView) rootView.findViewById(R.id.listViewPlaylist);
        DemoData demoData = new DemoData(getActivity());
        demoData.insertAudioData();
        populateListView();


        return rootView;
    }

    AudioListAdapter adapter;

    MediaPlayer mp;

    public void stopAudio() {
        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

    private void populateListView() {

        MotivatorDBHelper dbHelper = new MotivatorDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + MotivatorContract.MotivatorEntry.TABLE_AUDIO, null);
        final ArrayList<Audio> metaDataList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Audio audioMetadata = new Audio();
                audioMetadata.setAudioId(cursor.getString(0));
                audioMetadata.setAudioName(cursor.getString(1));
                audioMetadata.setFileAvailabilityInLocalMmy(cursor.getString(3));
                audioMetadata.setDownloadURL(cursor.getString(4));
                audioMetadata.setLocation(cursor.getString(2));

                // Adding contact to list
                metaDataList.add(audioMetadata);

            } while (cursor.moveToNext());
        }
        cursor.close();

        new DownloadAudio(getActivity(), metaDataList).execute();

//        booksListView = (ListView) rootView.findViewById(R.id.listViewLanguages);
        adapter = new AudioListAdapter(getActivity(), metaDataList);

        // Binds the Adapter to the ListView
        booksListView.setAdapter(adapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startDownload(mStoreListNames.get(position).getUrl(),mStoreListNames.get(position).getTitle());
                view.setSelected(true);
                selectedLanguage = metaDataList.get(position).getAudioName();
//                if (mp != null && mp.isPlaying()) {
//                    mp.stop();
//                    mp.release();
//                    mp = null;
//                }
//                mp = new MediaPlayer();
//
                try {
//                    if (metaDataList.get(position).getFileAvailabilityInLocalMmy().equals(Audio.AVAILABLE)) {
//                        if (metaDataList.get(position).getLocation().trim().length() > 0) {
//                            String filePathLink;// = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+worldpopulationlist.get(position).getLocation();
//                            filePathLink = Utility.getFilePathOnMobile(getActivity(), metaDataList.get(position).getLocation());
//                            mp.setDataSource(getActivity(), Uri.parse(filePathLink));
//                        }
//                    } else {
//                        mp.setDataSource(metaDataList.get(position).getDownloadURL());
//                        Toast.makeText(getActivity(), R.string.audioDownloadProgress,Toast.LENGTH_LONG).show();
//                    }
//                    mp.prepare();
//                    mp.start();
                    startService(metaDataList.get(position));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        stopAudio();
    }
    public void startService(Audio audio) {
        Intent serviceIntent = new Intent(getActivity(), NotificationService.class);
        serviceIntent.setAction(Constants2.ACTION.STARTFOREGROUND_ACTION);
        serviceIntent.putExtra("audioMetadata",audio);
        getActivity().startService(serviceIntent);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser == false)
            stopAudio();
    }


    @Override
    public void refreshList(Context context, String caller) {
        populateListView();

    }

}
