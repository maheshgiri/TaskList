package android.app.todoapp.pojo;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "task")
public class Task implements Serializable{
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo
    boolean isdailyreminder;
    @ColumnInfo
    boolean completed;
    @ColumnInfo
    long time;


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsdailyreminder() {
        return isdailyreminder;
    }

    public void setIsdailyreminder(boolean isdailyreminder) {
        this.isdailyreminder = isdailyreminder;
    }

    public Task clone(Task task){
        Task newtask=new Task();
        newtask.setId(task.getId());
        newtask.setCompleted(task.completed);
        newtask.setName(task.getName());
        newtask.setIsdailyreminder(task.isIsdailyreminder());
        return newtask;
    }
}
