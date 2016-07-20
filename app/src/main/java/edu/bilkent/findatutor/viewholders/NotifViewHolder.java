package edu.bilkent.findatutor.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import edu.bilkent.findatutor.R;
import edu.bilkent.findatutor.model.Notification;

/**
 * Created by arash on 7/20/16.
 */
public class NotifViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleView;
    private TextView mBodyView;
    private TextView mTagView;


    public NotifViewHolder(View itemView) {
        super(itemView);
        mTitleView = (TextView) itemView.findViewById(R.id.post_title);
        mBodyView = (TextView) itemView.findViewById(R.id.post_body);
        mTagView = (TextView) itemView.findViewById(R.id.tag_view);
    }


    public void bindToPost(Notification notif, View.OnClickListener clickListener) {
        mTitleView.setText(notif.getTitle());
        mBodyView.setText(notif.getText());
        mTagView.setText(notif.getTag());
    }
}
