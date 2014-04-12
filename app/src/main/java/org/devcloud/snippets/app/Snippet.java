package org.devcloud.snippets.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

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
  static final String COLUMN_TYPE_UUID = "text";
  static final String COLUMN_NAME_UUID = "uuid";

  static final String[] COLUMNS = new String[]{
      COLUMN_NAME_ID,
      COLUMN_NAME_TEXT,
      COLUMN_NAME_DATE,
      COLUMN_NAME_USERID,
      COLUMN_NAME_UUID
  };

  private String text;
  private Date created;
  private String userId;
  private long id;
  private String uuid;

  public Snippet(String msg, String userId) {
    this.setText(msg);
    this.setCreated(new Date());
    this.setUserId(userId);

    this.getUuid(); // Just to make sure there is one.

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


    String[] empty = {};
    Cursor cursor = db.query(
        TABLE_NAME,
        COLUMNS,
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
        snips.add(buildFromCursorRow(cursor));
      }

      cursor.close();
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
    }

    Log.i(TAG, "Built array: " + snips.toString());
    return snips;
  }

  private static Snippet buildFromCursorRow(Cursor cursor) {
    Snippet snip = new Snippet(
        cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_TEXT)),
        cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_USERID))
    );

    snip.setCreated(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_DATE)));
    snip.setId(cursor.getLong(cursor.getColumnIndex(Snippet.COLUMN_NAME_ID)));
    snip.setUuid(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_UUID)));

    return snip;
  }

  public static Snippet findByUUID(String uuid, Context context) {
    DatabaseHelper mDbHelper = new DatabaseHelper(context);
    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    assert db != null;

    String[] params = {uuid};
    Cursor cursor = db.query(
        Snippet.TABLE_NAME,
        COLUMNS,
        "uuid = ?",
        params,
        "", // Group By
        "", // Having
        "", // Order By
        "1" // Limit
    );

    if (cursor.getCount() == 0) {
      return null;
    } else {
      ArrayList<Snippet> snips = new ArrayList<Snippet>(cursor.getCount());
      for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
        snips.add(buildFromCursorRow(cursor));
      }

      return snips.get(0);
    }
  }

  public String getUuid() {
    if (uuid == null || uuid.equals("")) {
      setUuid(String.format("%s.%s",
          Settings.Secure.ANDROID_ID,
          UUID.randomUUID().toString()
      ));
    }

    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
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
    return String.format(
        "Snippet{uuid: '%s', text: '%s', created: %s, user_id: '%s'}",
        getUuid(),
        getText(),
        getCreated(),
        getUserId()
    );
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
    HashMap<String, String> map = new HashMap<String, String>(1);
    map.put("snippet_data", json.toString());
    new SyncTask().execute(map);

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
