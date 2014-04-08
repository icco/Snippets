package org.devcloud.snippets.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Snippet {
  public final static String TABLE_NAME = "snippets";

  final static String COLUMN_NAME_TEXT = "text";
  final static String COLUMN_TYPE_TEXT = "text";
  final static String COLUMN_NAME_DATE = "created";
  final static String COLUMN_TYPE_DATE = "datetime";
  static final String COLUMN_TYPE_USERID = "text";
  static final String COLUMN_NAME_USERID = "user_id";
  static final String COLUMN_NAME_ID = "_id";
  static final String TAG = "Snippet";

  private String text;
  private Date created;
  private String userId;
  private long id;

  public Snippet(String msg, String userId) {
    this.setText(msg);
    this.setCreated(new Date());
    this.setUserId(userId);

    Log.i(TAG, "Snippet instantiated: " + this.toString());
  }

  public Snippet(String msg) {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  public static Cursor getCursorForAll(Context context) throws IOException {
    DatabaseHelper mDbHelper = new DatabaseHelper(context);
    ArrayList<Snippet> list = new ArrayList<Snippet>();

    // Gets the data repository in write mode
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    if (db == null) {
      throw new IOException("Database not readable.");
    }

    String[] columns = {COLUMN_NAME_ID, COLUMN_NAME_TEXT, COLUMN_NAME_DATE, COLUMN_NAME_USERID};
    String[] empty = {};
    Cursor cursor = db.query(
        TABLE_NAME,
        columns,
        "",
        empty
        ,
        "",
        "",
        COLUMN_NAME_DATE + " DESC");

    return cursor;
  }

  static ArrayList<Snippet> getArrayListForAll(Context context) {
    ArrayList<Snippet> snips = new ArrayList<Snippet>();

    try {
      Cursor cursor = Snippet.getCursorForAll(context);
      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
        Snippet snip = new Snippet(
            cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_TEXT)),
            cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_USERID))
        );

        snip.setCreated(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_DATE)));
        snip.setId(cursor.getLong(cursor.getColumnIndex(Snippet.COLUMN_NAME_ID)));

        snips.add(snip);
      }

      cursor.close();
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
    }

    Log.i(TAG, "Built array: " + snips.toString());
    return snips;
  }

  private void setCreated(String string) {
    try {
      Date date = DatabaseHelper.parseDate(string);
      this.setCreated(date);
    } catch (ParseException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  @Override
  public String toString() {
    return "Snippet{" +
        "text: '" + getText() + '\'' +
        ", created: " + getCreated() +
        ", user_id: '" + getUserId() + '\'' +
        '}';
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
    values.put(this.COLUMN_NAME_USERID, this.getUserId());

    // Insert the new row, returning the primary key value of the new row
    this.setId(db.insert(this.TABLE_NAME, "", values));

    // Send a SyncTask to the server.
    JSONArray json = new JSONArray(Snippet.getArrayListForAll(context));
    new SyncTask().execute(json.toString());

    return this.getId();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String id) {
    this.userId = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
