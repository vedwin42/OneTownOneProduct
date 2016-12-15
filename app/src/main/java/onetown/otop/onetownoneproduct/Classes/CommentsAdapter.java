package onetown.otop.onetownoneproduct.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Objects.Comments;
import onetown.otop.onetownoneproduct.R;

/**
 * Created by EasyBreezy on 12/12/2016.
 */

public class CommentsAdapter extends ArrayAdapter<Comments>{

    ArrayList<Comments> commentses;
    Context ctx;

    public CommentsAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx=context;
    }

    public CommentsAdapter(Context context, int resource, ArrayList<Comments> commentsArrayList) {
        super(context, resource,commentsArrayList);
        this.commentses=commentsArrayList;
        this.ctx=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Comments  comments= getItem(position);

        LayoutInflater inflater= LayoutInflater.from(ctx);
        CommentsViewholder holder;

        if (convertView == null) {
            holder= new CommentsViewholder();
            convertView= inflater.inflate(R.layout.customcomment_layout,parent,false);
            holder.email= (TextView)convertView.findViewById(R.id.customlistview_username);
            holder.commentContent= (TextView)convertView.findViewById(R.id.customlistview_commentcontent);
            holder.timeStamp= (TextView)convertView.findViewById(R.id.customlistview_timestamp);
        }else {
            holder=(CommentsViewholder)convertView.getTag();
        }

        holder.email.setText(comments.getCurrentEmail());
        holder.commentContent.setText(comments.getCommentContent());
        holder.timeStamp.setText(comments.getCurrentTimeStamp());
        return convertView;
    }

    private static class CommentsViewholder{
        TextView email;
        TextView commentContent;
        TextView timeStamp;
    }
}
