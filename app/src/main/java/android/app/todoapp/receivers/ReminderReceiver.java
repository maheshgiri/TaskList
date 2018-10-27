package android.app.todoapp.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.ui.ReminderActivity;
import android.app.todoapp.utils.ManageAlarm;
import android.app.todoapp.utils.TaskDaoUtility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("DEBUG", "onReceive: " );



        if (ManageAlarm.checkCurrentTask(context)) {
            Intent intent1 = new Intent(context, ReminderActivity.class);
            intent1.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }


}
