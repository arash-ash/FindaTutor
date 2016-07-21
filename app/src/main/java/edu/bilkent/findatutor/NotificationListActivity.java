package edu.bilkent.findatutor;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import edu.bilkent.findatutor.model.Notification;
import edu.bilkent.findatutor.viewholders.NotifViewHolder;

/**
 * Created by arash on 7/16/16.
 */
public class NotificationListActivity extends BaseActivity {

    private static final String TAG = "NotifListActivity";

    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Notification, NotifViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;


    public NotificationListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
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
//        if (getIntent().getExtras() != null) {
//            for (String key : getIntent().getExtras().keySet()) {
//                String value = getIntent().getExtras().getString(key);
//                Log.d(TAG, "Key: " + key + " Value: " + value);
//            }
//        }
//        // [END handle_data_extras]


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = (RecyclerView) findViewById(R.id.notification_list);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query notifsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Notification, NotifViewHolder>(Notification.class, R.layout.item_notification,
                NotifViewHolder.class, notifsQuery) {
            @Override
            protected void populateViewHolder(final NotifViewHolder viewHolder, final Notification model, final int position) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // no action for now
                    }
                });

                // Bind Post to ViewHolder,
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Neeed to write to both places the post is stored
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

    }

    public Query getQuery(DatabaseReference databaseReference) {
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        return databaseReference.child("user-notifications").child(getUid())
                .limitToFirst(100);

    }


}
