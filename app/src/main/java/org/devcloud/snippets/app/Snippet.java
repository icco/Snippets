package org.devcloud.snippets.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;
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
  private String personId;

  public Snippet(String msg, String personId) {
    this.setText(msg);
    this.setCreated(new Date());
    this.setPersonId(personId);

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

    String[] columns = {"_id", COLUMN_NAME_TEXT, COLUMN_NAME_DATE};
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

  @Override
  public String toString() {
    return "Snippet{" +
        "text: '" + text + '\'' +
        ", created: " + created +
        ", personId: '" + personId + '\'' +
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

    // Insert the new row, returning the primary key value of the new row
    return db.insert(this.TABLE_NAME, "", values);
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String personId) {
    this.personId = personId;
  }
}
