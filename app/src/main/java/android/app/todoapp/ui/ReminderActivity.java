package android.app.todoapp.ui;

import android.app.todoapp.R;
import android.app.todoapp.adaptors.TaskRecylerAdaptor;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.utils.TaskDaoUtility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    private RecyclerView tasktocompletelist;
    private TaskRecylerAdaptor taskRecylerAdaptor;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        tasktocompletelist=findViewById(R.id.list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskList=TaskDaoUtility.getIntance(ReminderActivity.this).getAllTodaysLists();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskRecylerAdaptor=new TaskRecylerAdaptor(2,taskList);
                        tasktocompletelist.setAdapter(taskRecylerAdaptor);
                        tasktocompletelist.setLayoutManager(new LinearLayoutManager(ReminderActivity.this));
                    }
                });

            }
        }).start();

    }
}
