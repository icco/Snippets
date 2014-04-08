package org.devcloud.snippets.app;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nat on 4/8/14.
 */
public class SyncTask extends AsyncTask<Void, Void, JSONArray> {
  private static final String TAG = "SyncTask";

  @Override
  protected JSONArray doInBackground(Void... params) {
    try {
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost("https://snippets-app-api.herokuapp.com/user");

      // Add your data
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
      nameValuePairs.add(new BasicNameValuePair("id", "12345"));
      nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

      // Execute HTTP Post Request
      HttpResponse response = httpclient.execute(httppost);

      BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
      StringBuilder sb = new StringBuilder();
      sb.append(reader.readLine() + "\n");
      String line = "0";
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
      reader.close();
      String result11 = sb.toString();

      // parsing data
      return new JSONArray(result11);
    } catch (Exception e) {
      Log.e(TAG, e.getMessage(), e);
      return null;
    }
  }

  @Override
  protected void onPostExecute(JSONArray result) {
    if (result != null) {
      // do something
    } else {
      // error occured
    }
  }
}