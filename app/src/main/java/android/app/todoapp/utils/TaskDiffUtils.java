package android.app.todoapp.utils;

import android.app.todoapp.pojo.Task;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class TaskDiffUtils extends DiffUtil.Callback {

    List<Task> oldList;
    List<Task> newList;


    public TaskDiffUtils(List<Task> oldList,List<Task> newList){
        this.oldList=oldList;
        this.newList=newList;
    }


    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0 ;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0 ;
    }


    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Task newtask=newList.get(newItemPosition);
        Task oldtask=oldList.get(oldItemPosition);
        return newtask.getId()==newtask.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Task newtask=newList.get(newItemPosition);
        Task oldtask=oldList.get(oldItemPosition);
        return newtask.isCompleted()==oldtask.isCompleted();
    }
}
