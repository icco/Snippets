package org.devcloud.snippets.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {

  private static final String TAG = "BootReceiver";
  public static final String START_ALARM_ACTION = String.format("%s.START", BootReceiver.class.getCanonicalName());
  public static final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

  @Override
  public void onReceive(Context context, Intent intent) {
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    if (intent == null) {
      Log.d(TAG, "Received null intent.");
    } else {
      Log.d(TAG, String.format("Received intent: %s", intent));

      if (intent.getAction() == null) {
        Log.d(TAG, "Received null action.");
      } else {
        Log.d(TAG, String.format("Received action: %s", intent.getAction()));

        if (intent.getAction().equals(BOOT_ACTION) || intent.getAction().equals(START_ALARM_ACTION)) {
          alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

          Intent todo_intent = new Intent(context, WritePostAlarm.class);
          alarmIntent = PendingIntent.getBroadcast(context, 234324243, todo_intent, 0);

          alarmMgr.setInexactRepeating(
              AlarmManager.ELAPSED_REALTIME_WAKEUP,
              AlarmManager.INTERVAL_FIFTEEN_MINUTES, // When this first fires
              AlarmManager.INTERVAL_DAY, // How often it repeats
              alarmIntent
          );
        }
      }
    }
  }
}
