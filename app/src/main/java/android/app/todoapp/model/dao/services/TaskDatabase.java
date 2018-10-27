package android.app.todoapp.model.dao.services;

import android.app.todoapp.model.dao.TaskDao;
import android.app.todoapp.pojo.Comment;
import android.app.todoapp.pojo.Task;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = { Task.class, Comment.class },
        version = 1,exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
    private static volatile TaskDatabase INSTANCE;

    public static TaskDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "taskdb")
                            .allowMainThreadQueries()
                            .build();                }
            }
        }
        return INSTANCE;
    }
}
