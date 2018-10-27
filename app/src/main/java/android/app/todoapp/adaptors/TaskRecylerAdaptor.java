package android.app.todoapp.adaptors;

import android.app.todoapp.R;
import android.app.todoapp.adaptors.viewholders.CommentViewHolder;
import android.app.todoapp.adaptors.viewholders.TaskViewHolder;
import android.app.todoapp.pojo.Comment;
import android.app.todoapp.pojo.Task;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TaskRecylerAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Task> taskList=new ArrayList<>();
    private List<Comment> commentList;
    private int type=-1;



    public TaskRecylerAdaptor(List<Task> tasks){
        taskList=tasks;
    }

    public TaskRecylerAdaptor(int type,List<Task> tasks){
        taskList=tasks;
        this.type=type;
    }

    public TaskRecylerAdaptor(List<Comment> comments,int type){
        commentList=comments;
        this.type=type;
    }

    public void setData(List<Task> tasks){
        taskList=tasks;
        notifyItemInserted(taskList.size()-1);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        if (type==-1||type==2)
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.viewholder_task,parent,false));
        else return new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.viewholder_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (type==-1||type==2) {
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            if (type==2){
              taskViewHolder.bind(taskList.get(position),type);
            }else
            taskViewHolder.bind(taskList.get(position));

        }else {
            CommentViewHolder commentViewHolder= (CommentViewHolder) holder;
            commentViewHolder.bind(commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (type==-1||type==2)
        return taskList.size();
        else return commentList.size();
    }


}
