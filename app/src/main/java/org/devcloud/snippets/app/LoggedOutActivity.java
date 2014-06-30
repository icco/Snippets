package org.devcloud.snippets.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;

public class LoggedOutActivity extends FragmentActivity
    implements GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {

  private static final String TAG = "LoggedOutActivity";
  private static final int SIGN_IN_REQUEST_CODE = 12;

  public void signIn(View view) {

    Intent intent = AccountPicker.newChooseAccountIntent(
        null, null, new String[]{"com.google"},
        false, null, null, null, null
    );
    startActivityForResult(intent, SIGN_IN_REQUEST_CODE);
  }

  protected void onActivityResult(
      final int requestCode, final int resultCode,
      final Intent data
  ) {
    Intent intent;

    if (requestCode == SIGN_IN_REQUEST_CODE && resultCode == RESULT_OK) {
      String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
      Const.setUserId(this.getApplicationContext(), accountName);
      Log.w(TAG, String.format("Logged in: %s", accountName));
      intent = new Intent(this, MainActivity.class);
      startActivity(intent);
    } else {
      Log.i(TAG, "Signing out.");
      Const.deleteUserId(this.getApplicationContext());
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
