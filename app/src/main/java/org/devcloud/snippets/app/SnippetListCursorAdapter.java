package org.devcloud.snippets.app;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;

import static org.devcloud.snippets.app.BuildConfig.DEBUG;

class SnippetListCursorAdapter extends CursorAdapter {
  static final String TAG = "SnippetListCursorAdapter";

  private LayoutInflater mLayoutInflater;

  /**
   * Constructor that allows control over auto-requery.  It is recommended
   * you not use this, but instead {@link android.widget.CursorAdapter(android.content.Context, android.database.Cursor, int)}.
   * When using this constructor, {@link #FLAG_REGISTER_CONTENT_OBSERVER}
   * will always be set.
   *
   * @param context     The context
   * @param c           The cursor from which to get the data.
   * @param autoRequery If true the adapter will call requery() on the
   *                    cursor whenever it changes so the most recent
   */
  public SnippetListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
    super(context, c, autoRequery);
    mLayoutInflater = LayoutInflater.from(context);
  }

  /**
   * Makes a new view to hold the data pointed to by cursor.
   *
   * @param context Interface to application's global information
   * @param cursor  The cursor from which to get the data. The cursor is already
   *                moved to the correct position.
   * @param parent  The parent to which the new view is attached to
   *
   * @return the newly created view.
   */
  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return mLayoutInflater.inflate(R.layout.snippet_list_row, parent, false);
  }

  /**
   * Bind an existing view to the data pointed to by cursor
   *
   * @param view    Existing view, returned earlier by newView
   * @param context Interface to application's global information
   * @param cursor  The cursor from which to get the data. The cursor is already
   */
  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    try {
      TextView list_item_text = (TextView) view.findViewById(R.id.list_text);
      list_item_text.setText(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_TEXT)));

      TextView list_item_date = (TextView) view.findViewById(R.id.date);
      String date_text = cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_DATE));
      DateFormat df = DateFormat.getDateTimeInstance();
      list_item_date.setText(df.format(DatabaseHelper.parseDate(date_text)));

      if (DEBUG) {
        TextView list_item_user = (TextView) view.findViewById(R.id.user);
        list_item_user.setText(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_USERID)));
      } else {
        TextView list_item_user = (TextView) view.findViewById(R.id.user);
        list_item_user.setText(cursor.getString(cursor.getColumnIndex(Snippet.COLUMN_NAME_UUID)));
      }
    } catch (ParseException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }
}
