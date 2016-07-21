package edu.bilkent.findatutor;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.bilkent.findatutor.misc.CircleTransform;
import edu.bilkent.findatutor.model.Chat;
import edu.bilkent.findatutor.model.Notification;
import edu.bilkent.findatutor.model.Post;
import edu.bilkent.findatutor.model.Review;
import edu.bilkent.findatutor.model.Session;
import edu.bilkent.findatutor.model.User;
import edu.bilkent.findatutor.viewholders.CommentViewHolder;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_POST_KEY = "post_key";
    private static final String TAG = "PostDetailActivity";
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    public Button messageButton;
    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostPrice;
    private String mPostSchool;
    private String mPostLanguage;
    private String mPostAuthorPhotoURL;
    private String mPostAuthorUID;
    private String mPostKey;
    private String mPostTitle;
    private CommentAdapter mAdapter;
    private TextView mPostPriceView;
    private TextView mPostLanguageView;
    private TextView mPostSchoolView;
    private ImageView mPostAuthorPhoto;
    private TextView mAuthorView;
    private TextView mTitleView;
    private TextView mBodyView;
    private EditText mCommentField;
    private Button mCommentButton;
    private RecyclerView mCommentsRecycler;
    private DatabaseReference mDatabase;
    private EditText mDateField;
    private EditText mLocationField;
    private Spinner mDayTimeSpinner;
    private Session mSession;
    private NewSessionDialog dialog;
    private String mPostAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
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

        // Get post key from intent
        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        mPostTitle = getIntent().getStringExtra(ChatActivity.EXTRA_POST_TITLE);
        mPostAuthorUID = getIntent().getStringExtra(ChatActivity.EXTRA_POST_AUTHOR);
        mPostAuthorPhotoURL = getIntent().getStringExtra("photoURL");
        mPostPrice = getIntent().getStringExtra("price");
        mPostLanguage = getIntent().getStringExtra("language");
        mPostSchool = getIntent().getStringExtra("school");
        mPostAuthor = getIntent().getStringExtra("author");

        // Initialize Database
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(mPostKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-reviews").child(mPostKey);

        // Initialize Views
        mAuthorView = (TextView) findViewById(R.id.post_author);
        mTitleView = (TextView) findViewById(R.id.post_title);
        mBodyView = (TextView) findViewById(R.id.post_body);
        mCommentField = (EditText) findViewById(R.id.field_comment_text);
        mCommentButton = (Button) findViewById(R.id.button_post_comment);
        mCommentsRecycler = (RecyclerView) findViewById(R.id.recycler_comments);
        messageButton = (Button) findViewById(R.id.message_button);
        mPostAuthorPhoto = (ImageView) findViewById(R.id.post_author_photo);
        mPostPriceView = (TextView) findViewById(R.id.post_price);
        mPostLanguageView = (TextView) findViewById(R.id.post_language);
        mPostSchoolView = (TextView) findViewById(R.id.post_school);

        mPostPriceView.setText(mPostPrice);
        mPostSchoolView.setText(mPostSchool);
        mPostLanguageView.setText(mPostLanguage);

        Glide
                .with(PostDetailActivity.this)
                .load(mPostAuthorPhotoURL)
                .transform(new CircleTransform(getBaseContext()))
                .into(mPostAuthorPhoto);

        messageButton.setOnClickListener(this);
        mCommentButton.setOnClickListener(this);
        mCommentsRecycler.setLayoutManager(new LinearLayoutManager(this));


        findViewById(R.id.fab_new_session).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new NewSessionDialog(PostDetailActivity.this);
                dialog.show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post post = dataSnapshot.getValue(Post.class);
                // [START_EXCLUDE]
                mAuthorView.setText(post.authorName);
                mTitleView.setText(post.title);
                mBodyView.setText(post.body);
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(PostDetailActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mPostReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;

        // Listen for comments
        mAdapter = new CommentAdapter(this, mCommentsReference);
        mCommentsRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }

        // Clean up comments listener
        mAdapter.cleanupListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_post_comment:
                postComment();
                break;
            case R.id.message_button:
                chat();
                break;
        }
    }

    private void chat() {


        Chat chat = new Chat(mPostTitle, getName(), getUid(), mPostKey, getPhotoURL());
        Map<String, Object> chatValues = chat.toMap();
        String date = sDateFormat.format(new Date());
        mDatabase.child("user-messages").child(getUid()).child(mPostKey).setValue(chatValues);
        mDatabase.child("user-messages").child(mPostAuthorUID).child(mPostKey).setValue(chatValues);


        Intent intent = new Intent(PostDetailActivity.this, ChatActivity.class);
        intent.putExtra(ChatActivity.EXTRA_POST_KEY, mPostKey);
        intent.putExtra(ChatActivity.EXTRA_POST_TITLE, mPostTitle);
        intent.putExtra(ChatActivity.EXTRA_POST_USER, getUid());

        startActivity(intent);
    }

    private void postComment() {
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get modelUser information
                        User user = dataSnapshot.getValue(User.class);
                        String authorName = user.getUsername();

                        // Create new review object
                        String commentText = mCommentField.getText().toString();
                        Review review = new Review(uid, authorName, commentText, user.getPhotoURL());

                        // Push the review, it will appear in the list
                        mCommentsReference.push().setValue(review);

                        // Clear the field
                        mCommentField.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public String getUid() {

        return getInstance().getCurrentUser().getUid();
    }


    public String getName() {
        String email = getInstance().getCurrentUser().getEmail();
        return usernameFromEmail(email);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

        private Context mContext;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;

        private List<String> mCommentIds = new ArrayList<>();
        private List<Review> mReviews = new ArrayList<>();

        public CommentAdapter(final Context context, DatabaseReference ref) {
            mContext = context;
            mDatabaseReference = ref;

            // Create child event listener
            // [START child_event_listener_recycler]
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new review has been added, add it to the displayed list
                    Review review = dataSnapshot.getValue(Review.class);

                    // [START_EXCLUDE]
                    // Update RecyclerView
                    mCommentIds.add(dataSnapshot.getKey());
                    mReviews.add(review);
                    notifyItemInserted(mReviews.size() - 1);
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Review newReview = dataSnapshot.getValue(Review.class);
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Replace with the new data
                        mReviews.set(commentIndex, newReview);

                        // Update the RecyclerView
                        notifyItemChanged(commentIndex);
                    } else {
                        Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();

                    // [START_EXCLUDE]
                    int commentIndex = mCommentIds.indexOf(commentKey);
                    if (commentIndex > -1) {
                        // Remove data from the list
                        mCommentIds.remove(commentIndex);
                        mReviews.remove(commentIndex);

                        // Update the RecyclerView
                        notifyItemRemoved(commentIndex);
                    } else {
                        Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                    }
                    // [END_EXCLUDE]
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Review movedReview = dataSnapshot.getValue(Review.class);
                    String commentKey = dataSnapshot.getKey();

                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(mContext, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
            ref.addChildEventListener(childEventListener);
            // [END child_event_listener_recycler]

            // Store reference to listener so it can be removed on app stop
            mChildEventListener = childEventListener;
        }

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            Review review = mReviews.get(position);
            holder.authorView.setText(review.getAuthor());
            holder.bodyView.setText(review.getText());

            String url = review.getAuthorPhotoURL();
            Glide
                    .with(PostDetailActivity.this)
                    .load(url)
                    .transform(new CircleTransform(getBaseContext()))
                    .into(holder.authorPhoto);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        public void cleanupListener() {
            if (mChildEventListener != null) {
                mDatabaseReference.removeEventListener(mChildEventListener);
            }
        }

    }


    class NewSessionDialog extends Dialog implements android.view.View.OnClickListener {
        private Activity c;
        private Button yes, no;

        public NewSessionDialog(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_new_session);

            yes = (Button) findViewById(R.id.btn_yes);
            no = (Button) findViewById(R.id.btn_no);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);

            mDateField = (EditText) findViewById(R.id.edit_text_date);
            mLocationField = (EditText) findViewById(R.id.edit_text_location);
            mDateField.setOnClickListener(this);
            mDayTimeSpinner = (Spinner) findViewById(R.id.spinner_daytime);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(PostDetailActivity.this,
                    R.array.day_time_array, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mDayTimeSpinner.setAdapter(adapter1);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_text_date:
                    showDatePicker();
                    break;
                case R.id.btn_yes:
                    c.finish();
                    request();
                    break;
                case R.id.btn_no:
                    dismiss();
                    break;
                default:
                    break;
            }
        }


        private void request() {

            String date = sDateFormat.format(new Date());


            mSession = new Session(mPostAuthorUID, getUid(), mPostKey, mDateField.getText().toString(),
                    mDayTimeSpinner.getSelectedItem().toString(), mLocationField.getText().toString(),
                    mPostTitle, mPostAuthor, mPostAuthorPhotoURL);
            Map<String, Object> sessionValues = mSession.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/user-sessions/" + getUid() + "/" + date, sessionValues);
            childUpdates.put("/user-sessions/" + mPostAuthorUID + "/" + date, sessionValues);
            mDatabase.updateChildren(childUpdates);


            Map<String, Object> childUpdates2 = new HashMap<>();
            Notification mNotif = new Notification("New Session!", "session",
                    "You got a new session for " + mPostTitle);
            Map<String, Object> notifValues = mNotif.toMap();
            childUpdates2.put("/user-notifications/" + getUid() + "/" + date, notifValues);
            childUpdates2.put("/user-notifications/" + mPostAuthorUID + "/" + date, notifValues);
            mDatabase.updateChildren(childUpdates2);


            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"arash.asn94@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "New session request!");
            i.putExtra(Intent.EXTRA_TEXT, "Hi there, \n \n  I found your advertisement for " + mPostTitle + " on findAtutor app." +
                    " will you be able to have a session with me on " + mSession.getSessionDate() + " " + mSession.getDayTime() +
                    " at " + mSession.getPreferedLocation() + "?\n \n" + "Best Regards,\n" + getName());
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(PostDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
            startActivity(i);
        }

        private void showDatePicker() {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

                private void updateLabel() {

                    String myFormat = "MM/dd/yy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    mDateField.setText(sdf.format(myCalendar.getTime()));
                }
            };


            new DatePickerDialog(PostDetailActivity.this, datePicker, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

    }




}
