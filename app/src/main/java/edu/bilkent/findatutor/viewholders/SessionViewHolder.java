package edu.bilkent.findatutor.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import edu.bilkent.findatutor.R;
import edu.bilkent.findatutor.model.Session;

/**
 * Created by linus on 09.07.2016.
 */

public class SessionViewHolder extends RecyclerView.ViewHolder {


    public TextView titleView;
    public TextView tutorNameView;
    public ImageView imageView;


    public SessionViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.post_school);
        tutorNameView = (TextView) itemView.findViewById(R.id.post_author);
        imageView = (ImageView) itemView.findViewById((R.id.post_author_photo));
    }


    public void bindToPost(Session session, View.OnClickListener clickListener) {
        titleView.setText(session.getPostTitle());
        tutorNameView.setText(session.getTutorName());
    }
}
