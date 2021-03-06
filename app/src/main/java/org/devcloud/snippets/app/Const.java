package org.devcloud.snippets.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class with common utilities and constants needed across the app.
 */
public class Const {
  private static final String TAG = "Const";
  private static final String USER_ID_PREF_STRING = "user_id";

  static SharedPreferences getPreferences(Context context) {
    return context.getSharedPreferences("snippets_preferences", Context.MODE_PRIVATE);
  }

  static String getUserId(Context context) {
    SharedPreferences prefs = Const.getPreferences(context);
    return prefs.getString(USER_ID_PREF_STRING, "");
  }

  @SuppressLint("CommitPrefEdits")
  static void setUserId(Context context, String user_id) {
    SharedPreferences prefs = Const.getPreferences(context);
    prefs.edit().putString(USER_ID_PREF_STRING, user_id).apply();
  }

  @SuppressLint("CommitPrefEdits")
  public static void deleteUserId(Context context) {
    SharedPreferences prefs = Const.getPreferences(context);
    prefs.edit().remove(USER_ID_PREF_STRING).apply();
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

  /**
   * Tell us if we have any internet connection.
   *
   * @param context Current context.
   *
   * @return Whether or not we have a network connection.
   */
  static boolean isNetworkAvailable(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    return ni != null && ni.isConnected();
  }
}
