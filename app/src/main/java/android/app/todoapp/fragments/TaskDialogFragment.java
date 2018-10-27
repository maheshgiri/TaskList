package android.app.todoapp.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.todoapp.R;
import android.app.todoapp.interfaces.UpdateCallback;
import android.app.todoapp.pojo.Comment;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.utils.Constants;
import android.app.todoapp.utils.ManageAlarm;
import android.app.todoapp.utils.ScreenUtils;
import android.app.todoapp.utils.TaskDaoUtility;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskDialogFragment extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {

    private TextView lablfortaskname, taskname, remindme, txtvtitle;
    private CheckBox chb_dailyreminder;
    private Button button_cancel, button_create;
    private UpdateCallback updateCallback;
    private int hourofday, minute;
    private int type;
    private Task task;

    public static TaskDialogFragment getInstance(int type, Task task) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putSerializable("task", task);
        TaskDialogFragment taskDialogFragment = new TaskDialogFragment();
        taskDialogFragment.setArguments(bundle);
        return taskDialogFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_addtask, container, false);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view=LayoutInflater.from(getContext()).inflate(R.layout.dialogfragment_addtask,null,false);
        builder.setView(view);

        initView(view);

        return builder.create();
    }

    private void initView(View view) {
        type = getArguments().getInt("type", 0);
        task = (Task) getArguments().getSerializable("task");
      //  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        lablfortaskname = view.findViewById(R.id.lblname);
        button_cancel = view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);
        button_create = view.findViewById(R.id.button_create);
        button_create.setOnClickListener(this);
        taskname = view.findViewById(R.id.edittext_taskname);
        chb_dailyreminder = view.findViewById(R.id.chb_dailyreminder);
        chb_dailyreminder.setOnCheckedChangeListener(this);
        remindme = view.findViewById(R.id.remindme);
        remindme.setOnClickListener(this);
        remindme.setVisibility(View.GONE);

        txtvtitle = view.findViewById(R.id.txtvtitle);

        if (type == Constants.ADD_COMMENT) {
            if (task != null)
                txtvtitle.setText("ADD COMMENT FOR " + task.getName());
            lablfortaskname.setText("Enter comment ");
            chb_dailyreminder.setVisibility(View.GONE);
            remindme.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel:
                cancel();
                break;
            case R.id.button_create:
                createTask(view);
                break;
            case R.id.remindme:
                setTime();
                break;
        }
    }

    private void setTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    private void createTask(View view) {
        if (validate()) {
            if (type == Constants.ADD_COMMENT) {
                addComment();
            } else {
                addTask();
            }

            dismiss();

        } else
            Toast.makeText(getContext(), "Please enter valid task name!", Toast.LENGTH_SHORT).show();
    }

    private void addComment() {
        String str = taskname.getText().toString();
        if (!str.isEmpty()) {
            final Comment comment = new Comment();
            comment.setTaskid(task.getId());
            comment.setCommentstr(str);

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    TaskDaoUtility.getIntance(getContext()).insertComment(comment);
                    return null;
                }

            }.execute();

            dismiss();

        } else
            Toast.makeText(getContext(), "Please enter valid commment!", Toast.LENGTH_SHORT).show();
    }

    private void addTask() {
        final Task task = new Task();
        task.setIsdailyreminder(chb_dailyreminder.isChecked());
        task.setCompleted(false);
        task.setName(taskname.getText().toString());
        if (chb_dailyreminder.isChecked() && !remindme.getText().toString().isEmpty()) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourofday);
            calendar.set(Calendar.MINUTE, minute);
            task.setTime(calendar.getTime().getTime());
            ManageAlarm.setAlarm(calendar, getContext(), 1);
        }
        updateCallback = (UpdateCallback) getContext();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                TaskDaoUtility.getIntance(getContext()).insertTask(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                updateCallback.update(Constants.UPDATE, null);
            }
        }.execute();

    }

    private boolean validate() {
        if (!taskname.getText().toString().isEmpty()) return true;
        return false;
    }

    private void cancel() {
        dismiss();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourofday = i;
        minute = i1;
        remindme.setText("" + i + ":" + i1);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) remindme.setVisibility(View.VISIBLE);
        else remindme.setVisibility(View.GONE);
    }
}
