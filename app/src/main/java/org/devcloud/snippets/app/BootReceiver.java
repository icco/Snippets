package org.devcloud.snippets.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {

  private static final String TAG = "BootReceiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
      alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

      Intent todo_intent = new Intent(context, WritePostAlarm.class);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 234324243, todo_intent, 0);

      alarmMgr.setInexactRepeating(
          AlarmManager.ELAPSED_REALTIME_WAKEUP,
          AlarmManager.INTERVAL_FIFTEEN_MINUTES, // When this first fires
          AlarmManager.INTERVAL_DAY, // How often it repeats
          pendingIntent
      );
    } else {
      Log.d(TAG, String.format("Received intent: %s", intent));
    }
  }
}
