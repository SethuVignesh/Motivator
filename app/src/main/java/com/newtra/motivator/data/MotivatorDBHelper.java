package com.newtra.motivator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MotivatorDBHelper extends SQLiteOpenHelper {
	public static final String LOG_TAG = MotivatorDBHelper.class.getSimpleName();

	//name & version
	private static final String DATABASE_NAME = "motivators.db";
	public static final String AUDIO_AVAILABLE = "available";
	public static final String AUDIO_UNAVAILABLE = "unavailable";
	private static final int DATABASE_VERSION = 1;

	public MotivatorDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Create the database
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
				MotivatorContract.MotivatorEntry.TABLE_AUDIO + "(" + MotivatorContract.MotivatorEntry._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MotivatorContract.MotivatorEntry.COLUMN_AUDIO_NAME + " TEXT NOT NULL, " +
				MotivatorContract.MotivatorEntry.COLUMN_LOCATION_IN_LOCAL +
				" TEXT NOT NULL, " +
				MotivatorContract.MotivatorEntry.COLUMN_AUDIO_AVAILABILITY +
				" TEXT NOT NULL, " +
				MotivatorContract.MotivatorEntry.COLUMN_AUDIO_URL +
				" TEXT NOT NULL unique);";

		final String SQL_CREATE_TEXT_TABLE = "CREATE TABLE " +
				MotivatorContract.TextEntry.TABLE_TEXT + "(" + MotivatorContract.MotivatorEntry._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MotivatorContract.TextEntry.COLUMN_QUOTE +
				" TEXT NOT NULL unique);";
		final String SQL_CREATE_FLAVORS_TABLE = "CREATE TABLE " +
				MotivatorContract.FlavorEntry.TABLE_FLAVORS + "(" + MotivatorContract.FlavorEntry._ID +
				" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				MotivatorContract.FlavorEntry.COLUMN_VERSION_NAME + " TEXT NOT NULL, " +
				MotivatorContract.FlavorEntry.COLUMN_DESCRIPTION +
				" TEXT NOT NULL, " +
				MotivatorContract.FlavorEntry.COLUMN_ICON +
				" INTEGER NOT NULL);";

		sqLiteDatabase.execSQL(SQL_CREATE_FLAVORS_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
		sqLiteDatabase.execSQL(SQL_CREATE_TEXT_TABLE);
	}

	// Upgrade database when version is changed.
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
				newVersion + ". OLD DATA WILL BE DESTROYED");
		// Drop the table
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MotivatorContract.MotivatorEntry.TABLE_AUDIO);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
				MotivatorContract.MotivatorEntry.TABLE_AUDIO + "'");
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MotivatorContract.TextEntry.TABLE_TEXT);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
				MotivatorContract.TextEntry.TABLE_TEXT + "'");

		// re-create database
		onCreate(sqLiteDatabase);
	}
}
