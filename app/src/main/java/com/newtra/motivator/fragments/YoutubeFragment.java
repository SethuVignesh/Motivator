package com.newtra.motivator.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.newtra.motivator.beans.VideoList;
import com.newtra.motivator.R;
import com.newtra.motivator.activity.VideoListActivity;
import com.newtra.motivator.adapters.VideoListAdapter;

import java.util.ArrayList;


public class YoutubeFragment extends Fragment {
    public static final String STEVEJOBS = "stevejobs";
    public static final String ARNOLD = "arnold";
    public static final String WILLSMITH = "willsmith";
    public static final String BRUCELEE = "brucelee";
    public static final String BRIAN = "brian";
    ListView booksListView;
    private ProgressDialog mProgressDialog;
    public static String selectedLanguage;
    private AdView mAdView;
    public YoutubeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        MobileAds.initialize(getActivity(), "ca-app-pub-2392481904715687~7842877452");

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        populateListView(rootView);

        return rootView;
    }
    ArrayList<VideoList> mStoreListNames;

    private void populateListView(View rootView) {
        mStoreListNames= new ArrayList<VideoList>();
        mStoreListNames.add(new VideoList(STEVEJOBS,"Steve Jobs"));//Tamil
        mStoreListNames.add(new VideoList(ARNOLD,"Arnold Schwarzenegger"));//Englidh
        mStoreListNames.add(new VideoList(WILLSMITH,"Will Smith"));//Kannada
        mStoreListNames.add(new VideoList(BRUCELEE,"Bruce Lee's"));//TElugu
        mStoreListNames.add(new VideoList(BRIAN,"Brian Tracy"));//chinese


        booksListView =(ListView)rootView.findViewById(R.id.listViewLanguages);
        VideoListAdapter adapter = new VideoListAdapter(getActivity(), mStoreListNames);

        // Binds the Adapter to the ListView
        booksListView.setAdapter(adapter);
        /*booksListView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // GET the clicked book
                //check the folder if book available
                // if available open the book
                //else downlaod and save in the folder

            }
        });*/

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startDownload(mStoreListNames.get(position).getUrl(),mStoreListNames.get(position).getTitle());
                selectedLanguage=mStoreListNames.get(position).getTitle();
                Log.d("YOUTUBEFRAGMENT","Selected Language "+selectedLanguage);
                Intent intent = new Intent(getActivity(),VideoListActivity.class);
//                intent.putExtra("language",mStoreListNames.get(position).getTitle());

                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
