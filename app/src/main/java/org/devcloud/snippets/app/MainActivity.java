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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.IOException;


public class MainActivity extends FragmentActivity {

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
    SnippetListFragment list = (SnippetListFragment) getSupportFragmentManager().findFragmentById(list_fragment_id);

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
        list.refresh(context);
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
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void signOut(MenuItem item) {
    Intent intent = new Intent();

  }

  public void settingsClick(MenuItem item) {
    CharSequence text = "This is not implemented yet.";
    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
    toast.show();
  }
}