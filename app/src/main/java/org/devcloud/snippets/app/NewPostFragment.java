package org.devcloud.snippets.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Beginning fragment for saving a comment.
 */
public class NewPostFragment extends Fragment {

  public NewPostFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_post_snippet, container, false);
    return rootView;
  }
}