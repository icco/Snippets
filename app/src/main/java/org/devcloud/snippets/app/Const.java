package org.devcloud.snippets.app;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

/**
 * Class with common utilities and constants needed across the app.
 */
public class Const {
  private static final String TAG = "Const";

  static GoogleApiClient getGoogleApiClient(Activity activity) {
    GoogleApiClient.Builder client_builder = new GoogleApiClient.Builder(activity);

    client_builder.addApi(Plus.API, null);
    client_builder.addScope(Plus.SCOPE_PLUS_LOGIN);

    if (activity instanceof ConnectionCallbacks) {
      client_builder.addConnectionCallbacks((ConnectionCallbacks) activity);
    } else {
      Log.w(TAG, "Activity should implement ConnectionCallbacks");
    }

    if (activity instanceof OnConnectionFailedListener) {
      client_builder.addOnConnectionFailedListener((OnConnectionFailedListener) activity);
    } else {
      Log.w(TAG, "Activity should implement OnConnectionFailedListener");
    }

    return client_builder.build();
  }
}
