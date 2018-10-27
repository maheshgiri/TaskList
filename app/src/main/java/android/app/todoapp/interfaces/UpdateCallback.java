package android.app.todoapp.interfaces;

import android.app.todoapp.pojo.Task;

public interface UpdateCallback {

    void update(int type, Task task);
    void addComment(Task task);

}
