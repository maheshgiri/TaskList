package android.app.todoapp.ui;

import android.app.todoapp.R;
import android.app.todoapp.adaptors.TaskRecylerAdaptor;
import android.app.todoapp.pojo.Comment;
import android.app.todoapp.pojo.Task;
import android.app.todoapp.utils.TaskDaoUtility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private Task task;
    private List<Comment> commentList = new ArrayList<>();
    private RecyclerView commentlistrecyview;
    private TaskRecylerAdaptor taskRecylerAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        task = (Task) getIntent().getSerializableExtra("task");
        getSupportActionBar().setTitle("Comments for "+task.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        commentlistrecyview = findViewById(R.id.commentlist);
        new Thread(new Runnable() {
            @Override
            public void run() {
                commentList = TaskDaoUtility.getIntance(CommentActivity.this).getAllComments(task.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       taskRecylerAdaptor=new TaskRecylerAdaptor(commentList,1);
                       commentlistrecyview.setAdapter(taskRecylerAdaptor);
                       commentlistrecyview.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                    }
                });
            }
        }).start();

    }
}
