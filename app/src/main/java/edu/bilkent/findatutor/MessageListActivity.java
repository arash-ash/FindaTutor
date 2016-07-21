package edu.bilkent.findatutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import edu.bilkent.findatutor.misc.CircleTransform;
import edu.bilkent.findatutor.model.Chat;
import edu.bilkent.findatutor.viewholders.MessageViewHolder;

/**
 * Created by linus on 08.07.2016.
 */
public class MessageListActivity extends BaseActivity {

    private static final String TAG = "MessageListActivity";
    private DatabaseReference mDatabase;

    private FirebaseRecyclerAdapter<Chat, MessageViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public MessageListActivity() {
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

        if(drawer != null) {
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);





        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = (RecyclerView) findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Chat, MessageViewHolder>(Chat.class, R.layout.item_chat,
                MessageViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder, final Chat model, final int position) {

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch ChatActivity
                        Intent intent = new Intent(MessageListActivity.this, ChatActivity.class);
                        intent.putExtra(ChatActivity.EXTRA_POST_KEY, model.getPostUID());
                        intent.putExtra(ChatActivity.EXTRA_POST_TITLE, model.getTitle());
                        intent.putExtra(ChatActivity.EXTRA_POST_USER, model.getSenderUID());
                        startActivity(intent);
                    }
                });


                // Bind Post to ViewHolder,
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Neeed to write to both places the post is stored
                    }
                });


                String url = model.getSenderURL();
                Glide
                        .with(MessageListActivity.this)
                        .load(url)
                        .transform(new CircleTransform(getBaseContext()))
                        .into(viewHolder.imageView);
            }
        };
        mRecycler.setAdapter(mAdapter);

    }


    public Query getQuery(DatabaseReference databaseReference) {
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        return databaseReference.child("user-messages").child(getUid())
                .limitToFirst(100);

    }




}
