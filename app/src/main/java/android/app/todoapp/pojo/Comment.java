package android.app.todoapp.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "comments",foreignKeys = @ForeignKey(entity = Task.class,
        parentColumns = "id",
        childColumns = "taskid",
        onDelete = CASCADE))
public class Comment {
    @ForeignKey(entity = Task.class, parentColumns = "id", childColumns = "taskid")
    int taskid;

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    String commentstr;

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentstr() {
        return commentstr;
    }

    public void setCommentstr(String commentstr) {
        this.commentstr = commentstr;
    }
}
