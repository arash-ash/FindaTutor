package edu.bilkent.findatutor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import edu.bilkent.findatutor.model.Chat;
import edu.bilkent.findatutor.viewholders.MessageViewHolder;

/**
 * Created by arash on 7/16/16.
 */
public class NotificationListActivity extends BaseActivity {

    private static final String TAG = "NotifListActivity";
    private String mPostKey;

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Chat, MessageViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public NotificationListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);


        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]


//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mRecycler = (RecyclerView) findViewById(R.id.messages_list);
//        mRecycler.setHasFixedSize(true);

//        // Set up Layout Manager, reverse layout
//        mManager = new LinearLayoutManager(this);
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
//        mRecycler.setLayoutManager(mManager);

//        // Set up FirebaseRecyclerAdapter with the Query
//        Query postsQuery = getQuery(mDatabase);
//        mAdapter = new FirebaseRecyclerAdapter<Chat, MessageViewHolder>(Chat.class, R.layout.item_chat,
//                MessageViewHolder.class, postsQuery) {
//            @Override
//            protected void populateViewHolder(final MessageViewHolder viewHolder, final Chat model, final int position) {
//                final DatabaseReference postRef = getRef(position);
//
//                // Set click listener for the whole post view
//                final String postKey = postRef.getKey();
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Launch ChatActivity
//                        Intent intent = new Intent(NotificationListActivity.this, ChatActivity.class);
//                        intent.putExtra(ChatActivity.EXTRA_POST_KEY, mPostKey);
//                        intent.putExtra(ChatActivity.EXTRA_POST_TITLE, model.getTitle());
//                        intent.putExtra(ChatActivity.EXTRA_POST_USER, model.getSenderUID());
//                        startActivity(intent);
//                    }
//                });
//
//
//                // Bind Post to ViewHolder,
//                viewHolder.bindToPost(model, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View starView) {
//                        // Neeed to write to both places the post is stored
//                    }
//                });
//            }
//        };
//        mRecycler.setAdapter(mAdapter);

    }

//    public Query getQuery(DatabaseReference databaseReference) {
//        // Last 100 posts, these are automatically the 100 most recent
//        // due to sorting by push() keys
//        return databaseReference.child("users-posts-chatInfo").child(getUid()).child(mPostKey)
//                .limitToFirst(100);
//
//    }


}
