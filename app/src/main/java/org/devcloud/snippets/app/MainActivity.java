package org.devcloud.snippets.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

  public static final int REQUEST_CODE = 0;
  static final String SIGN_OUT_MESSAGE = "SIGN_OUT";
  private static final String TAG = "MainActivity";

  /**
   * Initial entry point into the class.
   *
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Make sure alarm has started...
    Intent alarmIntent = new Intent(getApplicationContext(), BootReceiver.class);
    startService(alarmIntent);

    // Build UI if we have no state.
    if (savedInstanceState == null) {
      Fragment list_fragment = new SnippetListFragment();

      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, list_fragment)
          .commit();
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

  public void aboutMenuItem(MenuItem item) {
    CharSequence text = "This is not implemented yet.";
    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
    toast.show();
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    boolean ret = super.onPrepareOptionsMenu(menu);
    MenuItem about_menu_item = menu.findItem(R.id.action_about);
    about_menu_item.setTitle(String.format("%s %s", getString(R.string.app_name), BuildConfig.VERSION_NAME));
    return ret;
  }

  public void newPost(View view) {
    Intent intent = new Intent(this, NewPostActivity.class);
    startActivity(intent);
  }
}