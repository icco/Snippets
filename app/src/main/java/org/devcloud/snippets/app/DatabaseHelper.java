package org.devcloud.snippets.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class DatabaseHelper extends SQLiteOpenHelper {

  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "Highlight.db";

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public static Date parseDate(String date) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    return dateFormat.parse(date);
  }

  public static String fmtDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    if (date == null) {
      date = new Date();
    }

    return dateFormat.format(date);
  }

  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE " + Snippet.TABLE_NAME + " (id INTEGER PRIMARY KEY," +
        Snippet.COLUMN_NAME_TEXT + " " + Snippet.COLUMN_TYPE_TEXT + " NOT NULL, " +
        Snippet.COLUMN_NAME_DATE + " " + Snippet.COLUMN_TYPE_DATE + " NOT NULL" +
        ")");
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL("DROP TABLE IF EXISTS " + Snippet.TABLE_NAME);
    onCreate(db);
  }

  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}