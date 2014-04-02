package org.devcloud.snippets.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Snippet {
  public final static String TABLE_NAME = "snippets";

  public final static String COLUMN_NAME_TEXT = "text";
  public final static String COLUMN_TYPE_TEXT = "text";
  public final static String COLUMN_NAME_DATE = "created";
  public final static String COLUMN_TYPE_DATE = "datetime";
  private static final String TAG = "Snippet";
  private String text;
  private Date created;

  public Snippet(String msg) {
    this.setText(msg);
    this.setCreated(new Date());
  }

  // TODO: Change this to this: https://developer.android.com/guide/topics/ui/layout/listview.html
  public static ArrayList<Snippet> loadAll(Context context) throws IOException {
    DatabaseHelper mDbHelper = new DatabaseHelper(context);
    ArrayList<Snippet> list = new ArrayList<Snippet>();

    // Gets the data repository in write mode
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    if (db == null) {
      throw new IOException("Database not readable.");
    }

    String[] columns = {COLUMN_NAME_TEXT, COLUMN_NAME_DATE};
    String[] empty = {};
    Cursor cursor = db.query(TABLE_NAME, columns, "", empty, "", "", COLUMN_NAME_DATE);

    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
      Snippet s = new Snippet(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TEXT)));
      String date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE));

      try {
        s.setCreated(DatabaseHelper.parseDate(date));
        list.add(s);
        Log.d(TAG, "Loaded an entry");
      } catch (ParseException e) {
        Log.e(TAG, "Couldn't parse date.", e);
        continue;
      }
    }

    return list;
  }

  public String toString() {
    return this.getText() + " - " + this.getCreated().toString();
  }

  public String getText() {
    return text;
  }

  private void setText(String text) {
    this.text = text;
  }

  public Date getCreated() {
    return created;
  }

  private void setCreated(Date created) {
    this.created = created;
  }

  public long save(Context context) throws IOException {
    DatabaseHelper mDbHelper = new DatabaseHelper(context);

    // Gets the data repository in write mode
    SQLiteDatabase db = mDbHelper.getWritableDatabase();

    if (db == null) {
      throw new IOException("Database not writable.");
    }

    // Create a new map of values, where column names are the keys
    ContentValues values = new ContentValues();
    values.put(this.COLUMN_NAME_TEXT, this.getText());
    values.put(this.COLUMN_NAME_DATE, DatabaseHelper.fmtDate(this.getCreated()));

    // Insert the new row, returning the primary key value of the new row
    return db.insert(this.TABLE_NAME, "", values);
  }
}
