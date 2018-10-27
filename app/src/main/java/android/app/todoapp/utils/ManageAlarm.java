package android.app.todoapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.receivers.ReminderReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class ManageAlarm {


    public static long lasttime;
    public static SharedPreferences sharedPreferences;
    public static long getLasttime() {

        if (sharedPreferences!=null)
        return sharedPreferences.getLong("lasttime",0);
        return 0;
    }


    public static void setAlarm(Calendar target, Context context, int reqcode){
       sharedPreferences =context.getSharedPreferences(
                "lasttime", Context.MODE_PRIVATE);
        lasttime=target.getTimeInMillis();
        sharedPreferences.edit().putLong("lasttime",lasttime);
        Intent intent = new Intent(context, ReminderReceiver.class);
        boolean alarmExists =
                (PendingIntent.getBroadcast(context, 0,
                        intent,
                        PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmExists){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reqcode, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(),0, pendingIntent);
        }
        Log.d("DEBUG", "setAlarm: ");
    }

    public static boolean checkCurrentTask(Context context){
        List<Task> taskList=TaskDaoUtility.getIntance(context).getAllLists();
        boolean result=false;
        for (Task task:taskList){
            if (task.getTime()!=0){
                result=true;
                break;
            }
        }
        return result;
    }
}
