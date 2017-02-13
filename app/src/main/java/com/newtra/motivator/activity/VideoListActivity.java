/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newtra.motivator.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.newtra.motivator.R;
import com.newtra.motivator.constants.DeveloperKey;
import com.newtra.motivator.fragments.YoutubeFragment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
 * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
 * <p>
 * The demo supports custom fullscreen and transitioning between portrait and landscape without
 * rebuffering.
 */
@TargetApi(13)
public final class VideoListActivity extends AppCompatActivity implements OnFullscreenListener {

    /**
     * The duration of the animation sliding up the video in portrait.
     */
    private static final int ANIMATION_DURATION_MILLIS = 300;
    /**
     * The padding between the video list and the video in landscape orientation.
     */
    private static final int LANDSCAPE_VIDEO_PADDING_DP = 5;

    /**
     * The request code when calling startActivityForResult to recover from an API service error.
     */
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private VideoListFragment listFragment;
    private VideoFragment videoFragment;

    private View videoBox;
    private View closeButton;

    private boolean isFullscreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.video_list_demo);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        myToolbar.setTitle("Video List");
        listFragment = (VideoListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
        videoFragment =
                (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);

        videoBox = findViewById(R.id.video_box);
        closeButton = findViewById(R.id.close_button);

        videoBox.setVisibility(View.INVISIBLE);

        layout();

        checkYouTubeApi();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void checkYouTubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMessage =
                    String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Recreate the activity if user performed a recovery action
            recreate();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        layout();
    }

    @Override
    public void onFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;

        layout();
    }

    /**
     * Sets up the layout programatically for the three different states. Portrait, landscape or
     * fullscreen+landscape. This has to be done programmatically because we handle the orientation
     * changes ourselves in order to get fluent fullscreen transitions, so the xml layout resources
     * do not get reloaded.
     */
    private void layout() {

        boolean isPortrait =
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        listFragment.getView().setVisibility(isFullscreen ? View.GONE : View.VISIBLE);
        listFragment.setLabelVisibility(isPortrait);
        closeButton.setVisibility(isPortrait ? View.VISIBLE : View.GONE);

        if (isFullscreen) {
            videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
            setLayoutSize(videoFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSizeAndGravity(videoBox, MATCH_PARENT, MATCH_PARENT, Gravity.TOP | Gravity.RIGHT);
        } else if (isPortrait) {
            setLayoutSize(listFragment.getView(), MATCH_PARENT, MATCH_PARENT);
            setLayoutSize(videoFragment.getView(), MATCH_PARENT, WRAP_CONTENT);
            setLayoutSizeAndGravity(videoBox, MATCH_PARENT, WRAP_CONTENT, Gravity.BOTTOM);
        } else {
            videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
            int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);
            setLayoutSize(listFragment.getView(), screenWidth / 4, MATCH_PARENT);
            int videoWidth = screenWidth - screenWidth / 4 - dpToPx(LANDSCAPE_VIDEO_PADDING_DP);
            setLayoutSize(videoFragment.getView(), videoWidth, WRAP_CONTENT);
            setLayoutSizeAndGravity(videoBox, videoWidth, WRAP_CONTENT,
                    Gravity.END | Gravity.CENTER_VERTICAL);
        }
    }

    public void onClickClose(@SuppressWarnings("unused") View view) {
        listFragment.getListView().clearChoices();
        listFragment.getListView().requestLayout();
        videoFragment.pause();
        ViewPropertyAnimator animator = videoBox.animate()
                .translationYBy(videoBox.getHeight())
                .setDuration(ANIMATION_DURATION_MILLIS);
        runOnAnimationEnd(animator, new Runnable() {
            @Override
            public void run() {
                videoBox.setVisibility(View.INVISIBLE);
            }
        });
    }

    @TargetApi(16)
    private void runOnAnimationEnd(ViewPropertyAnimator animator, final Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 16) {
            animator.withEndAction(runnable);
        } else {
            animator.setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    runnable.run();
                }
            });
        }
    }

    /**
     * A fragment that shows a static list of videos.
     */
    public static final class VideoListFragment extends ListFragment {


        private static final List<VideoEntry> STEVE_JOBS_VIDEO_LIST;
        private static final List<VideoEntry> ARNOLD_LIST;
        private static final List<VideoEntry> WILL_SMITH_LIST;
        private static final List<VideoEntry> BRUCE_LEE_LIST;
        private static final List<VideoEntry> BRIAN_TRACY_LIST;

        static {

            List<VideoEntry> arnold_list = new ArrayList<VideoEntry>();
            List<VideoEntry> steve_jobs_list = new ArrayList<VideoEntry>();
            List<VideoEntry> will_smith_list = new ArrayList<VideoEntry>();
            List<VideoEntry> bruce_lee_list = new ArrayList<VideoEntry>();
            List<VideoEntry> brian_list = new ArrayList<VideoEntry>();

            arnoldVideos(arnold_list);
            steveVideos(steve_jobs_list);
            willSmithVideos(will_smith_list);
            bruceLeeVideos(bruce_lee_list);
           brianVideos(brian_list);



            STEVE_JOBS_VIDEO_LIST = Collections.unmodifiableList(steve_jobs_list);
            ARNOLD_LIST = Collections.unmodifiableList(arnold_list);
            BRUCE_LEE_LIST = Collections.unmodifiableList(bruce_lee_list);
            WILL_SMITH_LIST = Collections.unmodifiableList(will_smith_list);
            BRIAN_TRACY_LIST = Collections.unmodifiableList(brian_list);

        }

        private static void arnoldVideos(List<VideoEntry> list) {
            list.add(new VideoEntry("'TRUST YOURSELF' (ft. Arnold Schwarzenegger)", "lHnTeyMAf80"));
            list.add(new VideoEntry("Arnold Schwarzenegger - Gym Motivation", "xoXYe9e01_Y"));
            list.add(new VideoEntry("The Best Arnold Schwarzenegger motivational speech (20 MINUTES+) 2016", "1sDH1UBXI44"));
            list.add(new VideoEntry("Arnold Schwarzenegger's Amazing Motivational Story", "wJPRj19OU-w"));
            list.add(new VideoEntry("Arnold Schwarzenegger: Life's 6 Rules - FULL SPEECH", "7TEc_qyKQ0c"));
            list.add(new VideoEntry("Arnold Schwarzenegger: The New Six Rules", "9Kg-zbJjlyA"));
            list.add(new VideoEntry("Arnold Schwarzenegger - Gym Motivation - Motivational Speech", "xoXYe9e01_Y&t=20s"));
        }

        private static void steveVideos(List<VideoEntry> list) {
            list.add(new VideoEntry("Steve Jobs Inspiring Speech - Most Important Life Lesson for All", "o5ZEgPIoZjw"));
            list.add(new VideoEntry("Stay hungry...Stay foolish. Amazing Steve Jobs Speech at Stanford", "gO6cFMRqXqU"));
            list.add(new VideoEntry("THINK DIFFERENT", "HQtZ4kud2qA"));
            list.add(new VideoEntry("'CHANGE THE WORLD' - Motivational video 2016", "c23OBLQFcCw"));
            list.add(new VideoEntry("MOTIVATION - Steve Jobs [FAILURE]", "jiAhKdYYhGM"));


        }
        private static void willSmithVideos(List<VideoEntry> list) {
            list.add(new VideoEntry("Motivational Speech from Pursuit of Happiness", "DvtxOzO6OAE"));
            list.add(new VideoEntry("'BELIEVE' (ft.Will Smith)", "9q__aQFmhig"));
        }
        private static void bruceLeeVideos(List<VideoEntry> list) {
            list.add(new VideoEntry("Bruce Lee's Top 10 Rules For Success", "u7tL8fK6tjA"));
            list.add(new VideoEntry("Bruce Lee - Motivation: Be You", "7uvmWG4U7QE"));
        }
        private static void brianVideos(List<VideoEntry> list) {
            list.add(new VideoEntry("Brian Tracy - 4 Ways To Change Your Life | FocalPoint Franchise", "a5xpUvKFePI"));
            list.add(new VideoEntry("Daily Habits of Successful People: It's All About Routine", "nu5I85_YAak"));
            list.add(new VideoEntry("3 Words That Can Change Your Life Forever", "nOA9w4LVlHY"));
            list.add(new VideoEntry("3 Ways to Improve Your Communication Skills", "D5hMN_XkPQA"));
            list.add(new VideoEntry("Secrets Of Self Made Millionaires by Brian Tracy", "rsT7k6A8rAQ"));
            list.add(new VideoEntry("Brian Tracy's Top 10 Rules For Success", "VCB3j438rNY"));
            list.add(new VideoEntry("Increasing Your Income 1000% Formula", "6Pz03hNEVTE"));
            list.add(new VideoEntry("Brian Tracy - Overcome Laziness", "B6H6KDNB8Pc"));
            list.add(new VideoEntry("Brian Tracy on Time Management", "sJb2qmd5wsk"));
            list.add(new VideoEntry("Tips to Structure Your Day", "4ysyybi4068"));

        }

        private PageAdapter adapter;
        private View videoBox;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            switch (YoutubeFragment.selectedLanguage) {
                case YoutubeFragment.STEVEJOBS:
                    adapter = new PageAdapter(getActivity(), STEVE_JOBS_VIDEO_LIST);
                    break;
                case YoutubeFragment.ARNOLD:
                    adapter = new PageAdapter(getActivity(), ARNOLD_LIST);
                    break;
                case YoutubeFragment.WILLSMITH:
                    adapter = new PageAdapter(getActivity(), WILL_SMITH_LIST);
                    break;
                case YoutubeFragment.BRUCELEE:
                    adapter = new PageAdapter(getActivity(), BRUCE_LEE_LIST);
                    break;
                case YoutubeFragment.BRIAN:
                    adapter = new PageAdapter(getActivity(), BRIAN_TRACY_LIST);
                    break;
                default:
                    adapter = new PageAdapter(getActivity(), STEVE_JOBS_VIDEO_LIST);

                    break;

            }

        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            videoBox = getActivity().findViewById(R.id.video_box);
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            setListAdapter(adapter);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {

            String videoId = null;
            switch (YoutubeFragment.selectedLanguage) {
                case YoutubeFragment.STEVEJOBS:
                    videoId = STEVE_JOBS_VIDEO_LIST.get(position).videoId;
                    break;
                case YoutubeFragment.ARNOLD:
                    videoId = ARNOLD_LIST.get(position).videoId;
                    break;
                case YoutubeFragment.WILLSMITH:
                    videoId = WILL_SMITH_LIST.get(position).videoId;
                    break;
                case YoutubeFragment.BRUCELEE:
                    videoId = BRUCE_LEE_LIST.get(position).videoId;
                    break;
                case YoutubeFragment.BRIAN:
                    videoId = BRIAN_TRACY_LIST.get(position).videoId;
                    break;

                default:
                    videoId = STEVE_JOBS_VIDEO_LIST.get(position).videoId;
                    break;

            }

            VideoFragment videoFragment =
                    (VideoFragment) getFragmentManager().findFragmentById(R.id.video_fragment_container);
            videoFragment.setVideoId(videoId);

            // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
            if (videoBox.getVisibility() != View.VISIBLE) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    // Initially translate off the screen so that it can be animated in from below.
                    videoBox.setTranslationY(videoBox.getHeight());
                }
                videoBox.setVisibility(View.VISIBLE);
            }

            // If the fragment is off the screen, we animate it in.
            if (videoBox.getTranslationY() > 0) {
                videoBox.animate().translationY(0).setDuration(ANIMATION_DURATION_MILLIS);
            }
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();

            adapter.releaseLoaders();
        }

        public void setLabelVisibility(boolean visible) {
            adapter.setLabelVisibility(visible);
        }

    }

    /**
     * Adapter for the video list. Manages a set of YouTubeThumbnailViews, including initializing each
     * of them only once and keeping track of the loader of each one. When the ListFragment gets
     * destroyed it releases all the loaders.
     */
    private static final class PageAdapter extends BaseAdapter {

        private final List<VideoEntry> entries;
        private final List<View> entryViews;
        private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
        private final LayoutInflater inflater;
        private final ThumbnailListener thumbnailListener;

        private boolean labelsVisible;

        public PageAdapter(Context context, List<VideoEntry> entries) {
            this.entries = entries;

            entryViews = new ArrayList<View>();
            thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
            inflater = LayoutInflater.from(context);
            thumbnailListener = new ThumbnailListener();

            labelsVisible = true;
        }

        public void releaseLoaders() {
            for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
                loader.release();
            }
        }

        public void setLabelVisibility(boolean visible) {
            labelsVisible = visible;
            for (View view : entryViews) {
                view.findViewById(R.id.text).setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public int getCount() {
            return entries.size();
        }

        @Override
        public VideoEntry getItem(int position) {
            return entries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            VideoEntry entry = entries.get(position);

            // There are three cases here
            if (view == null) {
                // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
                view = inflater.inflate(R.layout.video_list_item, parent, false);
                YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
                thumbnail.setTag(entry.videoId);
                thumbnail.initialize(DeveloperKey.DEVELOPER_KEY, thumbnailListener);
            } else {
                YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
                YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
                if (loader == null) {
                    // 2) The view is already created, and is currently being initialized. We store the
                    //    current videoId in the tag.
                    thumbnail.setTag(entry.videoId);
                } else {
                    // 3) The view is already created and already initialized. Simply set the right videoId
                    //    on the loader.
                    thumbnail.setImageResource(R.drawable.loading_thumbnail);
                    loader.setVideo(entry.videoId);
                }
            }
            TextView label = ((TextView) view.findViewById(R.id.text));
            label.setText(entry.text);
            label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
            return view;
        }

        private final class ThumbnailListener implements
                YouTubeThumbnailView.OnInitializedListener,
                YouTubeThumbnailLoader.OnThumbnailLoadedListener {

            @Override
            public void onInitializationSuccess(
                    YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
                loader.setOnThumbnailLoadedListener(this);
                thumbnailViewToLoaderMap.put(view, loader);
                view.setImageResource(R.drawable.loading_thumbnail);
                String videoId = (String) view.getTag();
                loader.setVideo(videoId);
            }

            @Override
            public void onInitializationFailure(
                    YouTubeThumbnailView view, YouTubeInitializationResult loader) {
                view.setImageResource(R.drawable.no_thumbnail);
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView view, ErrorReason errorReason) {
                view.setImageResource(R.drawable.no_thumbnail);
            }
        }

    }

    public static final class VideoFragment extends YouTubePlayerFragment
            implements OnInitializedListener {

        private YouTubePlayer player;
        private String videoId;

        public static VideoFragment newInstance() {
            return new VideoFragment();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            initialize(DeveloperKey.DEVELOPER_KEY, this);
        }

        @Override
        public void onDestroy() {
            if (player != null) {
                player.release();
            }
            super.onDestroy();
        }

        public void setVideoId(String videoId) {
            if (videoId != null && !videoId.equals(this.videoId)) {
                this.videoId = videoId;
                if (player != null) {
                    player.cueVideo(videoId);
                }
            }
        }

        public void pause() {
            if (player != null) {
                player.pause();
            }
        }

        @Override
        public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean restored) {
            this.player = player;
            player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            player.setOnFullscreenListener((VideoListActivity) getActivity());
            if (!restored && videoId != null) {
                player.cueVideo(videoId);
            }
        }

        @Override
        public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
            this.player = null;
        }

    }

    private static final class VideoEntry {
        private final String text;
        private final String videoId;

        public VideoEntry(String text, String videoId) {
            this.text = text;
            this.videoId = videoId;
        }
    }

    // Utility methods for layouting.

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static void setLayoutSize(View view, int width, int height) {
        LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        view.setLayoutParams(params);
    }

}
