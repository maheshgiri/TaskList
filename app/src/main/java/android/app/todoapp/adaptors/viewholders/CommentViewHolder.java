package android.app.todoapp.adaptors.viewholders;

import android.app.todoapp.R;
import android.app.todoapp.pojo.Comment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private TextView txtv_comment;

    public CommentViewHolder(View itemView) {
        super(itemView);
        txtv_comment=itemView.findViewById(R.id.txtv_comment);
    }

    public void bind(Comment comment){
        if (comment.getCommentstr()!=null&&!comment.getCommentstr().isEmpty())
        txtv_comment.setText(""+comment.getCommentstr());
    }
}
