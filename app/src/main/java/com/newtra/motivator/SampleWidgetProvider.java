package com.newtra.motivator;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.RemoteViews;

/**
 * Created by sethugayu on 11/4/16.
 */
public class SampleWidgetProvider extends AppWidgetProvider {
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //First up, the icon. I've created a file called h_yellow_x.png and placed it in /res/drawable
        int drawableResourse = R.drawable.logo;
        //Set Up the widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        //Set the image which will appear on the screen
        remoteViews.setImageViewResource(R.id.imageView, drawableResourse );
        Intent active = new Intent(context, SampleWidgetProvider.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView, actionPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
    MediaPlayer mPlay;
    @Override
    public void onReceive(Context context, Intent intent) {
        // v1.5 fix that doesn't call onDelete Action
        final String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
            //The widget is being deleted off the desktop
            final int appWidgetId = intent.getExtras().getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                this.onDeleted(context, new int[] { appWidgetId });
            }
        } else {
            // check, if our Action was called
            if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {

                //Play the audio file
                //The audio file is in /res/raw/ and is an OGG file
                if(mPlay!=null){
                    mPlay.stop();
                }
                 mPlay = MediaPlayer.create(context, R.raw.sample);
                mPlay.start();
            } else {
                // do nothing
            }


            super.onReceive(context, intent);
        }
    }
}