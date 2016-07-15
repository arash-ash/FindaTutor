package edu.bilkent.findatutor;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.bilkent.findatutor.model.Message;

/**
 * Created by linus on 08.07.2016.
 */

public class MessageSource {
    private static final String TAG = "MessageSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");

    public static void saveMessage(Message message, String uid, String postKey, String email){
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getText());
        msg.put(COLUMN_SENDER, email);
        mDatabase.child("posts").child(postKey).child("users").child(uid).child("messages").child(key).setValue(msg);
    }

    public static void saveRequestedMessage(Message message, String uid, String postKey, String email){
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getText());
        msg.put(COLUMN_SENDER, email);
        mDatabase.child("posts-requested").child(postKey).child("users").child(uid).child("messages").child(key).setValue(msg);

    }




    public static MessagesListener addMessagesListener(String uid, final MessagesCallbacks callbacks, String postKey){
        MessagesListener listener = new MessagesListener(callbacks);
        mDatabase.child("posts").child(postKey).child("users").child(uid).child("messages").addChildEventListener(listener);
        return listener;
    }

    public static MessagesListener addRequestedMessagesListener(String uid, final MessagesCallbacks callbacks, String postKey){
        MessagesListener listener = new MessagesListener(callbacks);
        mDatabase.child("posts-requested").child(postKey).child("users").child(uid).child("messages").addChildEventListener(listener);
        return listener;
    }


    public static void stop(MessagesListener listener){
        mDatabase.removeEventListener(listener);
    }

    public interface MessagesCallbacks {
        void onMessageAdded(Message message);
    }

    public static class MessagesListener implements ChildEventListener {
        private MessagesCallbacks callbacks;
        MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg = (HashMap)dataSnapshot.getValue();
            Message message = new Message();
            message.setSender(msg.get(COLUMN_SENDER));
            message.setText(msg.get(COLUMN_TEXT));
            try {
                message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            }catch (Exception e){
                Log.d(TAG, "Couldn't parse date" + e);
            }
            if(callbacks != null){
                callbacks.onMessageAdded(message);
            }
        }
        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {}

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    }
}