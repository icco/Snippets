package org.devcloud.snippets.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class NewPostActivity extends FragmentActivity {

  private static final String TAG = "NewPostActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar ab = getActionBar();
    if (ab != null) {
      ab.setTitle("New Snippet");
    }

    setContentView(R.layout.activity_new_post);

    if (savedInstanceState == null) {
      Fragment f = new NewPostFragment();
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, f)
          .commit();
    }
  }


  /**
   * Saves a message stored in an edit text widget.
   *
   * @param view
   */
  public void saveMessage(View view) {
    // Get the text.
    EditText editText = (EditText) findViewById(R.id.edit_message);

    String message = editText.getText().toString();
    Context context = getApplicationContext();

    try {
      // Save the text.
      if (!message.isEmpty()) {
        Snippet snippet = new Snippet(message, Const.getUserId(context));
        snippet.save(context);

        // Intent back to Main.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
      } else {
        CharSequence text = "Snippets can not be empty.";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
      }
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.new_post, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void closeKeyboard(View view) {
    try {
      InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    } catch (NullPointerException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  public void editTextOnTap(View view) {
    EditText textView = (EditText) view.findViewById(R.id.edit_message);

    textView.setFocusableInTouchMode(true);
    textView.setFocusable(true);

    InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

    textView.requestFocus();
    imm.showSoftInput(textView, 0);
  }
}