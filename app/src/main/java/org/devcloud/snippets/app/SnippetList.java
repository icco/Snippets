package org.devcloud.snippets.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;


public class SnippetList extends FragmentActivity {

  static final String TAG = "SnippetList";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_snippet_list);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .add(R.id.container, new SnippetListFragment())
          .commit();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.snippet_list, menu);
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

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class SnippetListFragment extends ListFragment {

    public SnippetListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_snippet_list, container, false);

      try {
        ArrayAdapter<Snippet> snippet_list;
        ArrayList<Snippet> snippets = Snippet.loadAll(this.getActivity().getApplicationContext());

        snippet_list = new ArrayAdapter<Snippet>(inflater.getContext(), android.R.layout.simple_list_item_1, snippets);
        setListAdapter(snippet_list);
      } catch (IOException e) {
        Log.e(TAG, "Problem loading data from DB.", e);
      }
      return rootView;
    }
  }
}
