package android.app.todoapp.ui;

import android.app.todoapp.R;
import android.app.todoapp.adaptors.TaskRecylerAdaptor;
import android.app.todoapp.fragments.TaskDialogFragment;
import android.app.todoapp.interfaces.UpdateCallback;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.utils.Constants;
import android.app.todoapp.utils.TaskDaoUtility;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements UpdateCallback {

    private RecyclerView taskrecyview;
    private TaskRecylerAdaptor taskRecylerAdaptor;
    private List<Task> oldList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskrecyview = findViewById(R.id.recyview_todolist);

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        taskRecylerAdaptor = new TaskRecylerAdaptor(TaskDaoUtility.getIntance(TaskActivity.this).getAllLists());
                        taskrecyview.setLayoutManager(new LinearLayoutManager(TaskActivity.this, LinearLayoutManager.VERTICAL, false));
                        taskrecyview.setAdapter(taskRecylerAdaptor);
                    }
                });
            }
        }).start();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialogFragment fragment = TaskDialogFragment.getInstance(Constants.ADD_TASK,null);
                fragment.show(getSupportFragmentManager(), "ADDTASK");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "onResume: ");
        updateAdaptor();
    }

    private void updateAdaptor() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (taskRecylerAdaptor != null){
                    List<Task> taskList=TaskDaoUtility.getIntance(TaskActivity.this).getAllLists();
                    taskRecylerAdaptor.setData(taskList);
                }
            }
        });
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.setting:
                //();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void update(int type, Task task) {
        if (type == Constants.MARK_COMPLETE)
            markComplete(task);

        if (type == Constants.SHOWCOMMENTS)
            showComments(task);

        if (type==Constants.UPDATE) {
            List<Task> taskList=TaskDaoUtility.getIntance(this).getAllLists();
            taskRecylerAdaptor.setData(taskList);
            taskrecyview.smoothScrollToPosition(taskList.size()-1);
        }
    }

    @Override
    public void addComment(final Task task) {
        TaskDialogFragment taskDialogFragment=TaskDialogFragment.getInstance(Constants.ADD_COMMENT,task);
        taskDialogFragment.show(getSupportFragmentManager(),"ADDCOMMENTFRAGMENT");
    }

    private void markComplete(Task task) {
        task.setCompleted(!task.isCompleted());
        TaskDaoUtility.getIntance(this).update(task);
        updateAdaptor();
    }

    private void showComments(Task task) {
        Intent startcommentintent=new Intent(this,CommentActivity.class);
        startcommentintent.putExtra("task",task);
        startActivity(startcommentintent);
    }
}
