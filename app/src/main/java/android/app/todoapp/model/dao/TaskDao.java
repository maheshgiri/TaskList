package android.app.todoapp.model.dao;

import android.app.todoapp.pojo.Comment;
import android.app.todoapp.pojo.Task;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM comments WHERE taskid=:taskid")
    List<Comment> getAllComments(int taskid);

    @Query("SELECT * from TASK where completed=0")
    List<Task> getAllLists();

    @Query("SELECT * from TASK where time!=0 and completed=0")
    List<Task> getAllTodaysLists();

    @Insert
    void insertTask(Task task);

    @Update
    void update(Task task);

    @Insert
    void insertComment(Comment comment);


}
