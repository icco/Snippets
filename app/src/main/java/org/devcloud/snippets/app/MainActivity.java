package org.devcloud.snippets.app;

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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

import java.io.IOException;

public class MainActivity extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {

  static final String SIGN_OUT_MESSAGE = "SIGN_OUT";
  private static final String TAG = "MainActivity";
  private int post_fragment_id, list_fragment_id;

  private void buildUI() {
    Fragment list_fragment = new SnippetListFragment();
    Fragment post_fragment = new NewPostFragment();

    getSupportFragmentManager().beginTransaction()
        .add(R.id.container, post_fragment)
        .add(R.id.container, list_fragment)
        .commit();

    // Save IDs
    list_fragment_id = list_fragment.getId();
    post_fragment_id = post_fragment.getId();
  }


  /**
   * Initial entry point into the class.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      // Now we wait for onConnected to be called
      buildUI();
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

        // Reload view
        editText.setText("");
        SnippetListFragment list = (SnippetListFragment) getSupportFragmentManager().findFragmentById(list_fragment_id);
        if (list != null && context != null) {
          list.refresh(context);
        } else {
          if (list == null) {
            Log.e(TAG, "list is null");
          }

          if (context == null) {
            Log.e(TAG, "context is null");
          }
        }
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
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    return id == R.id.action_settings || super.onOptionsItemSelected(item);
  }

  public void signOutMenuItem(MenuItem item) {
    Intent intent = new Intent(this, LoggedOutActivity.class);
    intent.putExtra(SIGN_OUT_MESSAGE, true);
    startActivity(intent);
  }

  public void settingsMenuItem(MenuItem item) {
    CharSequence text = "This is not implemented yet.";
    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
    toast.show();
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

  public void closeKeyboard(View view) {
    try {
      InputMethodManager imm = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    } catch (NullPointerException e) {
      Log.e(TAG, e.getMessage(), e);
    }
  }

  @Override
  public void onConnected(Bundle bundle) {

  }

  @Override
  public void onDisconnected() {

  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

  }
}