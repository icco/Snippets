package org.devcloud.snippets.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Beginning fragment for saving a comment.
 */
public class NewPostFragment extends Fragment {

  public NewPostFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_post_snippet, container, false);
    setNameAsEditable(rootView, true);
    return rootView;
  }

  private void setNameAsEditable (View rowView, boolean setToEditable) {

    EditText textView = (EditText) rowView
        .findViewById(R.id.edit_message);
    textView.setFocusableInTouchMode(setToEditable);
    textView.setFocusable(setToEditable);

    InputMethodManager imm = (InputMethodManager) rowView.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

    if (setToEditable) {
      textView.requestFocus();
      imm.showSoftInput(textView, 0);
    }
  }
}