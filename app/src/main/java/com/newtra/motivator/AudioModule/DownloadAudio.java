package com.newtra.motivator.AudioModule;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.newtra.motivator.Beans.Audio;
import com.newtra.motivator.Constants;
import com.newtra.motivator.Utility;
import com.newtra.motivator.data.MotivatorContract;
import com.newtra.motivator.data.MotivatorDBHelper;
import com.newtra.motivator.fragments.AudioFragment;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DownloadAudio extends AsyncTask<String, Void, Void> {
    private Context mContext;
    private MotivatorDBHelper dbHelper;
    SQLiteDatabase db;
    //    private RefreshListView = new AudioActivity();
    ArrayList<Audio> audioArrayList;
    private String TAG = DownloadAudio.class.getSimpleName();
    SharedPreferences prefs;
    private RefreshListView mRefreshListView = new AudioFragment();

    public DownloadAudio(Context mContext, ArrayList<Audio> audioArrayList) {
        this.mContext = mContext;
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.audioArrayList = audioArrayList;
        dbHelper = new MotivatorDBHelper(mContext);
        db = dbHelper.getWritableDatabase();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Audio Download optimise :DownloadAudio Asynctask:onPreExecute() ===========");


    }

    @Override
    protected Void doInBackground(String... params) {
        for (Audio metadata : audioArrayList) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (metadata.getFileAvailabilityInLocalMmy().equals(Audio.UNAVAILABLE)) {
                downloadAudioFile(metadata);
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "Audio Download optimise :DownloadAudio Asynctask i.e read from DB All downloaded :onPostExceute()");
    }

    private void downloadAudioFile(Audio metadata) {

        Log.d(TAG, "optimize Audio downlaod:DownloadAudio AsyncTask");

        String _url = metadata.getDownloadURL();

        int count;
        try {
            URL url = new URL(_url);
            String fileName;
//            fileName = metadata.getAudioName();
            fileName = System.currentTimeMillis()+"";
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            // getting file length
            int lenghtOfFile = connection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(Utility.getFilePathOnMobile(mContext, fileName));
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // writing data to file
                output.write(data, 0, count);
            }
            // flushing output
            output.flush();
            // closing streams
            output.close();
            input.close();
//            String filePathLink = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+fileName;
            //TODO UPDATE DB AS FILE DOWNLOADED IN EARBOX
            metadata.setFileAvailabilityInLocalMmy(Audio.AVAILABLE);
            metadata.setLocation(fileName);
            ContentValues args = new ContentValues();
            args.put(MotivatorContract.MotivatorEntry._ID, metadata.getAudioId());
            args.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, metadata.getAudioName());
            args.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, metadata.getDownloadURL());
            args.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_AVAILABLE);
            args.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, fileName);


            db.update(MotivatorContract.MotivatorEntry.TABLE_AUDIO, args, MotivatorContract.MotivatorEntry._ID + "=" + metadata.getAudioId(), null);
            mRefreshListView.refreshList(mContext, Constants.REFRESH_DOWNLAODED_AUDIO);
//            Log.d("AsnynTask", "Audio Download optimise :Audio file store at  AsyncTask" + filePathLink);

        } catch (Exception e) {
            Log.e("Error: ", "Audio file" + e.getMessage());
        }
    }
}
