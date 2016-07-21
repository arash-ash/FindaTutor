package edu.bilkent.findatutor.misc;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bilkent.findatutor.MessageListActivity;
import edu.bilkent.findatutor.NotificationListActivity;
import edu.bilkent.findatutor.R;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by arash on 7/20/16.
 */
public class NotificationsListener extends Application {
    private DatabaseReference mDatabase;

    public NotificationsListener() {
        // this method fires only once per application start.
        // getApplicationContext returns null here

        Log.i("main", "Constructor fired");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // this method fires once as well as constructor
        // but also application has context here
        if (getUid() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("user-notifications").child(getUid()).addChildEventListener(new NotificationChildEventListener());
            //mDatabase.child("user-messages").child(getUid()).addChildEventListener(new MessagesChildEventListener());
            Log.i("main", "onCreate fired");
        }
    }

    private void sendMessageNotification(String messageBody) {
        Intent intent = new Intent(this, MessageListActivity.class);
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

    public String getUid() {

        return getInstance().getCurrentUser().getUid();
    }

    class NotificationChildEventListener extends ChildEventAdaptor {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            sendNotification("You got a new notification!");
        }
    }

    class MessagesChildEventListener extends ChildEventAdaptor {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            sendMessageNotification("You got a new chat!");
        }
    }
}
