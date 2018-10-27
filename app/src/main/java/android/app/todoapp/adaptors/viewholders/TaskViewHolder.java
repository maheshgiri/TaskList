package android.app.todoapp.adaptors.viewholders;

import android.app.todoapp.R;
import android.app.todoapp.interfaces.UpdateCallback;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.ui.TaskActivity;
import android.app.todoapp.utils.Constants;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private TextView taskname, txtv_comments, txtv_addcomment, txtv_tasktime;
    private CheckBox chk_markcomplete;
    private UpdateCallback updateCallback;


    public TaskViewHolder(View itemView) {
        super(itemView);
        taskname = itemView.findViewById(R.id.txtv_taskname);
        chk_markcomplete = itemView.findViewById(R.id.chk_markcomplete);
        txtv_comments = itemView.findViewById(R.id.txtv_viewcomments);
        txtv_comments.setOnClickListener(this);
        chk_markcomplete.setOnClickListener(this);
        if (itemView.getContext() instanceof TaskActivity)
            updateCallback = (UpdateCallback) itemView.getContext();
        txtv_addcomment = itemView.findViewById(R.id.txtv_addcomment);
        txtv_tasktime = itemView.findViewById(R.id.txtv_tasktime);
        txtv_addcomment.setOnClickListener(this);
    }


    public void bind(Task task) {
        taskname.setText("" + task.getName());
        taskname.setTag(task);
        if (task.isCompleted())
            chk_markcomplete.setChecked(true);
        else chk_markcomplete.setChecked(false);
        chk_markcomplete.setTag(task);
        txtv_addcomment.setTag(task);
        txtv_comments.setTag(task);
        if (task.isIsdailyreminder()) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTimeInMillis(task.getTime());
            txtv_tasktime.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + "  ");
        } else txtv_tasktime.setText("");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chk_markcomplete:
                markComplete(view);
                break;
            case R.id.txtv_viewcomments:
                showComments(view);
                break;
            case R.id.txtv_addcomment:
                addComment(view);

        }
    }

    private void addComment(View view) {
        updateCallback.addComment((Task) view.getTag());
    }

    private void markComplete(View view) {
        if (updateCallback != null)
            updateCallback.update(Constants.MARK_COMPLETE, (Task) view.getTag());
    }

    private void showComments(View view) {
        if (updateCallback != null)
            updateCallback.update(Constants.SHOWCOMMENTS, (Task) view.getTag());
    }

    public void bind(Task task, int type) {
        taskname.setText("" + task.getName());
        chk_markcomplete.setVisibility(View.GONE);
        chk_markcomplete.setVisibility(View.GONE);
        txtv_addcomment.setVisibility(View.GONE);
        txtv_comments.setVisibility(View.GONE);
        txtv_tasktime.setVisibility(View.GONE);
    }
}
