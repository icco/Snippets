package org.devcloud.snippets.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class with common utilities and constants needed across the app.
 */
public class Const {
  private static final String TAG = "Const";
  private static final String USER_ID_PREF_STRING = "user_id";

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

  static SharedPreferences getPreferences(Context context) {
    return context.getSharedPreferences("snippets_preferences", Context.MODE_PRIVATE);
  }

  static String getUserId(Context context) {
    SharedPreferences prefs = Const.getPreferences(context);
    return prefs.getString(USER_ID_PREF_STRING, "");
  }

  static void setUserId(Context context, String user_id) {
    SharedPreferences prefs = Const.getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(USER_ID_PREF_STRING, user_id);
    editor.commit();
  }

  public static void deleteUserId(Context context) {
    SharedPreferences prefs = Const.getPreferences(context);
    SharedPreferences.Editor editor = prefs.edit();
    editor.remove(USER_ID_PREF_STRING);
    editor.commit();
  }

  /**
   * @param route URI path to hit starting with a /.
   */
  static String getServerUrl(String route) throws MalformedURLException, URISyntaxException {
    String host;
    URI url;
    if (BuildConfig.DEBUG) {
      host = "https://snippets-app-api-test.herokuapp.com";
    } else {
      host = "https://snippets-app-api.herokuapp.com";
    }

    url = new URI(host + route);
    return url.toURL().toString();
  }
}
