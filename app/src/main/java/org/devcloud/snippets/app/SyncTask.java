package org.devcloud.snippets.app;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncTask extends AsyncTask<HashMap<String, String>, Void, ArrayList<Snippet>> {
  private static final String TAG = "SyncTask";

  @Override
  protected ArrayList<Snippet> doInBackground(HashMap<String, String>... params) {
    String result_string = "[]";
    ArrayList<Snippet> ret = new ArrayList<Snippet>();

    try {
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost(Const.getServerUrl("/user"));

      // Join the hash-maps into a single list
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
      for (HashMap<String, String> map : params) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
          Log.i(TAG, String.format("Setting \"%s\" => \"%s\"", entry.getKey(), entry.getValue()));
          nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
      }
      httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

      // Execute HTTP Post Request
      HttpResponse response = httpclient.execute(httppost);
      if (response.getStatusLine().getStatusCode() == 200) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        sb.append(reader.readLine()).append("\n");
        String line = "0";
        while ((line = reader.readLine()) != null) {
          sb.append(line).append("\n");
        }
        reader.close();
        result_string = sb.toString();
        Log.i(TAG, "Got response: " + result_string);
      } else {
        Log.w(TAG, "Bad Response: " + response.getStatusLine().toString());
      }

      // parsing the data
      Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
      ArrayList parsed = gson.fromJson(result_string, ArrayList.class);
      for (Object i : parsed) {
        Log.d(TAG, "Parsed had: " + i.toString());
      }
    } catch (ClientProtocolException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (UnsupportedEncodingException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (IOException e) {
      Log.e(TAG, e.getMessage(), e);
    } catch (URISyntaxException e) {
      Log.e(TAG, e.getMessage(), e);
    }

    return ret;
  }

  @Override
  protected void onPostExecute(ArrayList<Snippet> result) {
    if (result != null) {
      Log.i(TAG, "Post was Successful.");
    } else {
      Log.e(TAG, "Post could not be completed. Will try again with next save.");
    }
  }
}