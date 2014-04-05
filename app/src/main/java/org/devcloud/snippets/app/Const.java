package org.devcloud.snippets.app;

import android.app.Activity;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 * Created by nat on 4/5/14.
 */
public class Const {
  static GoogleApiClient getApiClient(Activity activity) {
    if (!(activity instanceof ConnectionCallbacks)) {
      throw new IllegalArgumentException("Activity must implement ConnectionCallbacks");
    }

    if (!(activity instanceof OnConnectionFailedListener)) {
      throw new IllegalArgumentException("Activity must implement OnConnectionFailedListener");
    }

    return new GoogleApiClient.Builder(activity)
        .addConnectionCallbacks((ConnectionCallbacks) activity)
        .addOnConnectionFailedListener((OnConnectionFailedListener) activity)
        .addApi(Plus.API, null)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
  }
}
