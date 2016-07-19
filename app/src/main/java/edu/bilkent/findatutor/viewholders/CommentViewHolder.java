package edu.bilkent.findatutor.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.bilkent.findatutor.R;

/**
 * Created by arash on 7/19/16.
 */
public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView bodyView;
    public ImageView authorPhoto;


    public CommentViewHolder(View itemView) {
        super(itemView);

        authorView = (TextView) itemView.findViewById(R.id.comment_author);
        bodyView = (TextView) itemView.findViewById(R.id.comment_body);
        authorPhoto = (ImageView) itemView.findViewById(R.id.comment_photo);
    }
}