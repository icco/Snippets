package org.devcloud.snippets.app;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nat on 4/8/14.
 */
public class SyncTask extends AsyncTask<HashMap<String, String>, Void, JSONArray> {
  private static final String TAG = "SyncTask";

  @Override
  protected JSONArray doInBackground(HashMap<String, String>... params) {
    try {
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost("https://snippets-app-api.herokuapp.com/user");

      // Join the hashmaps into a single list
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      for (HashMap<String, String> map : params) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
          nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
      }
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
      Log.i(TAG, "Got the following response: " + result11);

      // parsing data

      return new JSONArray(result11);
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (ClientProtocolException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (UnsupportedEncodingException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
    }

    return null;
  }

  @Override
  protected void onPostExecute(JSONArray result) {
    if (result != null) {
      Log.i(TAG, "Post was Successful.");
    } else {
      Log.e(TAG, "Post could not be completed. Will try again with next save.");
    }
  }
}