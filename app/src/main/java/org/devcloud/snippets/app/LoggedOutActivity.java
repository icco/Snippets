package org.devcloud.snippets.app;

import android.app.Activity;
import android.os.Bundle;

public class LoggedOutActivity extends PlusBaseActivity {

  /**
   * Called when the {@link PlusClient} revokes access to this app.
   */
  @Override
  protected void onPlusClientRevokeAccess() {

  }

  /**
   * Called when the PlusClient is successfully connected.
   */
  @Override
  protected void onPlusClientSignIn() {

  }

  /**
   * Called when the {@link PlusClient} is disconnected.
   */
  @Override
  protected void onPlusClientSignOut() {

  }

  /**
   * Called when the {@link PlusClient} is blocking the UI.  If you have a progress bar widget,
   * this tells you when to show or hide it.
   *
   * @param show
   */
  @Override
  protected void onPlusClientBlockingUI(boolean show) {

  }

  /**
   * Called when there is a change in connection state.  If you have "Sign in"/ "Connect",
   * "Sign out"/ "Disconnect", or "Revoke access" buttons, this lets you know when their states
   * need to be updated.
   */
  @Override
  protected void updateConnectButtonState() {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_logged_out);
  }
}
