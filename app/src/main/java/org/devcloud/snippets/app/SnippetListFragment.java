package org.devcloud.snippets.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public static class SnippetListFragment extends ListFragment {

  static final String TAG = "SnippetListFragment";


  public SnippetListFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_snippet_list, container, false);

    try {
      SnippetListCursorAdapter snippet_list;
      snippet_list = new SnippetListCursorAdapter(
          inflater.getContext(),
          Snippet.getCursorForAll(inflater.getContext()),
          true);
      setListAdapter(snippet_list);
    } catch (IOException e) {
      Log.e(TAG, "Problem loading data from DB.", e);
    }
    return rootView;
  }
}