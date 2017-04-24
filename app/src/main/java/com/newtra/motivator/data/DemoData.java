package com.newtra.motivator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sethugayu on 1/12/17.
 */

public class DemoData {
    Context context;

    public DemoData(Context context) {

        this.context = context;
    }

    public void insertAudioData() {
        MotivatorDBHelper dbHelper = new MotivatorDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Second Step: Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        List<ContentValues> testValues = createAudioValues();

        // Third Step: Insert ContentValues into database and get a row ID back
        long locationRowId;
        for (int i = 0; i < testValues.size(); i++) {

            db.insert(MotivatorContract.MotivatorEntry.TABLE_AUDIO, null, testValues.get(i));
        }


    }

    public void insertTextData() {
        MotivatorDBHelper dbHelper = new MotivatorDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContentValues> testValues = createTextValues();
        for (int i = 0; i < testValues.size(); i++) {

            db.insert(MotivatorContract.TextEntry.TABLE_TEXT, null, testValues.get(i));
        }


    }

    static List<ContentValues> createTextValues() {
        // Create a new map of values, where column names are the keys
        List<ContentValues> audioDataList = new ArrayList<>();
        ContentValues testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work. And the only way to do great work is to love what you do. If you haven't found it yet, keep looking. Don't settle. As with all matters of the heart, you'll know when you find it.");

        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Innovation distinguishes between a leader and a follower.");

        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "My favorite things in life don’t cost any money. It’s really clear that the most precious resource we all have is time.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "I’m as proud of many of the things we haven’t done as the things we have done. Innovation is saying no to a thousand things.");
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Have the courage to follow your heart and intuition. They somehow know what you truly want to become.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "I think if you do something and it turns out pretty good, then you should go do something else wonderful, not dwell on it for too long. Just figure out what’s next.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Sometimes when you innovate, you make mistakes. It is best to admit them quickly, and get on with improving your other innovations.");
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Most People Motivational Video For The Minority.mp3");
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "When you’re a carpenter making a beautiful chest of drawers, you’re not going to use a piece of plywood on the back, even though it faces the wall and nobody will see it. You’ll know it’s there, so you’re going to use a beautiful piece of wood on the back. For you to sleep well at night, the aesthetic, the quality, has to be carried all the way through.\n");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "That’s been one of my mantras—focus and simplicity. Simple can be harder than complex; you have to work hard to get your thinking clean to make it simple.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Quality is more important than quantity. One home run is much better than two doubles.\n");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Being the richest man in the cemetery doesn’t matter to me. Going to bed at night saying we’ve done something wonderful...that’s what matters to me.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "The people who are crazy enough to think they can change the world are the ones who do.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Be a yardstick of quality. Some people aren’t used to an environment where excellence is expected.");
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "We’re just enthusiastic about what we do.");
        audioDataList.add(testValues);


        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Things don’t have to change the world to be important.\n");
        audioDataList.add(testValues);


        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Technology is nothing. What’s important is that you have a faith in people, that they’re basically good and smart, and if you give them tools, they’ll do wonderful things with them.");
        audioDataList.add(testValues);


        testValues = new ContentValues();
        testValues.put(MotivatorContract.TextEntry.COLUMN_QUOTE, "Getting fired from Apple was the best thing that could have ever happened to me. The heaviness of being successful was replaced by the lightness of being a beginner again. It freed me to enter one of the most creative periods of my life.");
        audioDataList.add(testValues);


        return audioDataList;
    }

    static List<ContentValues> createAudioValues() {
        // Create a new map of values, where column names are the keys
        List<ContentValues> audioDataList = new ArrayList<>();
        ContentValues testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "23 The Three Major Obstacles to Growth according to Brian Tracy");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/Motivator/raw/master/audio/23%20The%20Three%20Major%20Obstacles%20to%20Growth%20according%20to%20Brian%20Tracy.mp3");

        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "Arnold Schwarzenegger - Gym Motivation - Motivational Speech");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/Arnold%20Schwarzenegger%20-%20Gym%20Motivation%20-%20Motivational%20Speech.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);

        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "BrianTracy_Goals_Disk01");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/BrianTracy_Goals_Disk01.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "Entrepreneur [ the Self Made ] Epic Motivational Speech.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/Entrepreneur%20%5B%20the%20Self%20Made%20%5D%20Epic%20Motivational%20Speech.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "How To OVERCOME Failure & Make an IMPACT - Motivational Video 2017.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/How%20To%20OVERCOME%20Failure%20&%20Make%20an%20IMPACT%20-%20Motivational%20Video%202017.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "IT'S TIME TO CHANGE - 2017 New Years Motivation.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/IT'S%20TIME%20TO%20CHANGE%20-%202017%20New%20Years%20Motivation.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "Lone Wolf - Motivational Video For All Those Fighting Battles Alone.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/Lone%20Wolf%20-%20Motivational%20Video%20For%20All%20Those%20Fighting%20Battles%20Alone.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "Most People Motivational Video For The Minority.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/Most%20People%20Motivational%20Video%20For%20The%20Minority.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);
        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "RITheThreeMajorObstaclestoGrowthaccordingtoBrianTracyJune132009.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/RITheThreeMajorObstaclestoGrowthaccordingtoBrianTracyJune132009.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "THE BILLIONAIRE MINDSET - Motivational Video (from the great achievers of the world).mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/THE%20BILLIONAIRE%20MINDSET%20-%20Motivational%20Video%20(from%20the%20great%20achievers%20of%20the%20world).mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "THE MINDSET BEHIND SUCCESS - Motivational Video.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/THE%20MINDSET%20BEHIND%20SUCCESS%20-%20Motivational%20Video.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "THEY WERE WRONG - Motivational Video.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/THEY%20WERE%20WRONG%20-%20Motivational%20Video.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "The Hero In You - Motivational Video.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/The%20Hero%20In%20You%20-%20Motivational%20Video.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "The Lion Attitude (HEART OF A LION) Motivational Video.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, "https://github.com/SethuVignesh/MotivatorFilesRepo/blob/master/audio/The%20Lion%20Attitude%20(HEART%20OF%20A%20LION)%20Motivational%20Video.mp3?raw=true");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);

        testValues = new ContentValues();
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME, "rich-dad_64kb.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL, "");

        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL, " https://raw.githubusercontent.com/SethuVignesh/MotivatorFilesRepo/master/audio/rich-dad_64kb.mp3");
        testValues.put(MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY, MotivatorDBHelper.AUDIO_UNAVAILABLE);
        audioDataList.add(testValues);


        return audioDataList;
    }

}
