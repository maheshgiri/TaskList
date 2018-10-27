package android.app.todoapp.utils;

import android.app.todoapp.model.dao.TaskDao;
import android.app.todoapp.model.dao.services.TaskDatabase;
import android.content.Context;

public class TaskDaoUtility {

    static TaskDao taskDao;

    public static TaskDao getIntance(Context context){
        if (taskDao==null)taskDao=TaskDatabase.getDatabase(context).getTaskDao();
        return taskDao;
    }
}
