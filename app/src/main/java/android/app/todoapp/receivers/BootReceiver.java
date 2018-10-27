package android.app.todoapp.receivers;

import android.app.todoapp.ui.ReminderActivity;
import android.app.todoapp.utils.ManageAlarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Schedule on last time inserted
        Log.d("DEBUG", "onReceive: ");
        if (ManageAlarm.checkCurrentTask(context)) {
            Calendar calendar = GregorianCalendar.getInstance();
            if (ManageAlarm.sharedPreferences == null)
                ManageAlarm.sharedPreferences = context.getSharedPreferences(
                        "lasttime", Context.MODE_PRIVATE);

            calendar.setTimeInMillis(ManageAlarm.getLasttime());
            ManageAlarm.setAlarm(calendar, context, 1);
        }
    }
}
