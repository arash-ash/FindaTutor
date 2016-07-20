package edu.bilkent.findatutor;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import edu.bilkent.findatutor.model.Message;

import static edu.bilkent.findatutor.MessageSource.MessagesCallbacks;
import static edu.bilkent.findatutor.MessageSource.MessagesListener;
import static edu.bilkent.findatutor.MessageSource.addMessagesListener;
import static edu.bilkent.findatutor.MessageSource.saveMessage;
import static edu.bilkent.findatutor.MessageSource.stop;

/**
 * Created by linus on 08.07.2016.
 */


public class ChatActivity extends BaseActivity implements View.OnClickListener,
        MessagesCallbacks {

    public static final String USER_EXTRA = "USER";
    public static final String EXTRA_POST_KEY = "post_key";
    public static final String EXTRA_POST_TITLE = "post_title";
    public static final String EXTRA_POST_USER = "extra_post_user";
    public static final String EXTRA_POST_AUTHOR = "post_author";


    public static final String TAG = "ChatActivity";


    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;

    private ListView mListView;
    private Date mLastMessageDate = new Date();
    private String uid;
    private MessagesListener mListener;
    private String mPostKey;
    private String mPostTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if(drawer != null) {
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);






        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        mPostTitle = getIntent().getStringExtra(EXTRA_POST_TITLE);
        uid = getIntent().getStringExtra(EXTRA_POST_USER);


        mListView = (ListView)findViewById(R.id.posts_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);
        setTitle(mPostTitle);


        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);


        mListener = addMessagesListener(uid, this, mPostKey);

    }

    public void onClick(View v) {
        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText(newMessage);
        msg.setSender(getEmail());

        saveMessage(msg, uid, mPostKey, getEmail());
    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);
        mAdapter.notifyDataSetChanged();
        //sendNotification(message.getSender() + " said: " + message.getText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop(mListener);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, NotificationListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.findatutor_icon)
                .setContentTitle("Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private class MessagesAdapter extends ArrayAdapter<Message> {
        MessagesAdapter(ArrayList<Message> messages){
            super(ChatActivity.this, R.layout.item_message, R.id.message, messages);
        }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            Message message = getItem(position);

            TextView nameView = (TextView)convertView.findViewById(R.id.message);
            nameView.setText(message.getText());

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

            int sdk = Build.VERSION.SDK_INT;
            if (message.getSender().equals(getEmail())){
                nameView.setBackgroundResource(R.drawable.bubble_right_green);

                layoutParams.gravity = Gravity.RIGHT;
            }else{
                nameView.setBackgroundResource(R.drawable.bubble_left_gray);
                layoutParams.gravity = Gravity.LEFT;
            }

            nameView.setLayoutParams(layoutParams);


            return convertView;
        }
    }
}